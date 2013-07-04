package bacci.giovanni.deunifier.DeUniFier.pipelines.unifier;

import bacci.giovanni.deunifier.DeUniFier.freq.BasicFrequencyTag;
import bacci.giovanni.deunifier.DeUniFier.freq.FrequencyTags;
import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.io.FastaSequenceReader;
import bacci.giovanni.deunifier.DeUniFier.io.FrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SequenceReader;
import bacci.giovanni.deunifier.DeUniFier.io.SequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;

public class TagFileCounterMod extends FileCounter {
	private FrequencyTags freq = null;
	private long uniques;
	private final int NUM_THREAD;
	private Semaphore semaphore = null;

	public TagFileCounterMod(List<Path> list, int numThread) {
		super(list);
		this.NUM_THREAD = numThread;
		this.semaphore = new Semaphore(this.NUM_THREAD);
		String[] tags = new String[list.size()];
		for (int i = 0; i < tags.length; i++) {
			tags[i] = list.get(i).getFileName().toString();
		}
		this.freq = new BasicFrequencyTag(tags);
	}

	public void writeOutput(SequenceWriter wr, FrequencyWriter tableWriter,
			Writer idWriter) throws IOException {
		this.scannedSeq = 0L;
		Iterator<Entry<Sequence, TaggedFrequency>> it = getIterator();
		boolean header = true;
		setDoing("Begin writing... this could take a long time...");
		while (it.hasNext()) {
			Entry<Sequence, TaggedFrequency> entry = it.next();
			tableWriter.write(entry, header);
			header = false;
			runWorker(new SearchingThread(wr, idWriter, entry));
			it.remove();
		}
	}

	private void runWorker(SearchingThread worker) {
		try {
			this.semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(worker).start();
	}

	private Iterator<Entry<Sequence, TaggedFrequency>> getIterator()
			throws IOException {
		setDoing("Searching for redundant sequences:");
		SequenceReader reader = null;
		for (Path p : pathList) {
			setDoing(p.toString());
			BufferedReader br = Files.newBufferedReader(p,
					Charset.defaultCharset());
			reader = new FastaSequenceReader(br);
			String tag = p.getFileName().toString();
			while (reader.hasNext()) {
				this.freq.addSequence(reader.nextSequence(false), tag);
				this.numSeq += 1L;
			}
		}
		setDoing("Done:");
		this.uniques = this.freq.getUniqueCount();
		setDoing("Found: " + this.uniques + " unique sequences on "
				+ this.numSeq + " total sequences");
		double rate = (this.numSeq - this.uniques) * (100.0D / this.numSeq);
		String res = String.format("%.2f%s of sequences were redundant", rate,
				"%");
		setDoing(res);
		return this.freq.getEntrySetIterator();
	}

	private Path searchFile(String name) {
		for (Path p : this.pathList) {
			if (p.getFileName().toString().equals(name)) {
				return p;
			}
		}
		return null;
	}

	class SearchingThread implements Runnable {
		private SequenceWriter wr = null;
		private Writer idWriter = null;
		private Entry<Sequence, TaggedFrequency> entry = null;

		public SearchingThread(SequenceWriter wr, Writer idWriter,
				Entry<Sequence, TaggedFrequency> entry) {
			this.wr = wr;
			this.idWriter = idWriter;
			this.entry = entry;
		}

		public void run() {
			StringBuffer id = new StringBuffer();
			boolean first = true;
			try {
				for (String tag : this.entry.getValue().getTags()) {
					long seqNum = this.entry.getValue().getCount(tag);
					if (seqNum > 0L) {
						Path path = searchFile(tag);
						BufferedReader br = Files.newBufferedReader(path,
								Charset.defaultCharset());
						SequenceReader reader = new FastaSequenceReader(br);
						while ((reader.hasNext()) && (seqNum > 0L)) {
							Sequence s = reader.nextSequence(true);
							if (this.entry.getKey().compareTo(s) == 0) {
								if (first) {
									first = false;
									writeSequence(s, this.wr);
									id.append(s.getId() + "\t"
											+ s.getSequence());
								} else {
									id.append("\t" + s.getId());
								}
								id.append(System.lineSeparator());
								seqNum -= 1L;
							}
						}
						br.close();
					}
				}
				writeId(id.toString(), this.idWriter);
				incrementProgress();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected synchronized void setDoing(String doing) {
		setChanged();
		notifyObservers(doing);
	}

	protected synchronized void incrementProgress() {
		this.scannedSeq += 1L;
		double prog = 100.0D - (this.uniques - this.scannedSeq)
				* (100.0D / this.uniques);
		setChanged();
		notifyObservers(Double.valueOf(prog));
		this.semaphore.release();
	}
}