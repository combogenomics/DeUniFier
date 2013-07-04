package bacci.giovanni.deunifier.DeUniFier.pipelines.unifier;

import bacci.giovanni.deunifier.DeUniFier.freq.BasicTaggedFrequency;
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
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

public class FileStreamCounter extends FileCounter {
	private String[] tags = null;
	private Map<String, List<Long>> scannedMap = null;
	private Semaphore semaphore = null;

	public FileStreamCounter(List<Path> list, int numThread) {
		super(list);
		this.tags = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			this.tags[i] = ((Path) list.get(i)).getFileName().toString();
		}
		this.scannedMap = new HashMap<String, List<Long>>();
		for (String s : this.tags) {
			this.scannedMap.put(s, new ArrayList<Long>());
		}
		this.semaphore = new Semaphore(numThread);
	}

	public void writeOutput(SequenceWriter wr, FrequencyWriter tableWriter,
			Writer idWriter) throws IOException {
		boolean header = true;

		for (int i = 0; i < this.pathList.size(); i++) {
			setDoing("Scanning " + this.pathList.get(i) + "...");
			this.scannedSeq = 0L;
			BufferedReader br1 = Files.newBufferedReader(
					(Path) this.pathList.get(i), Charset.defaultCharset());
			SequenceReader mainReader = new FastaSequenceReader(br1);
			String t = ((Path) this.pathList.get(i)).getFileName().toString();

			while (mainReader.hasNext()) {
				Sequence s1 = mainReader.nextSequence(true);
				this.scannedSeq += 1L;

				if (!alredyScanned(this.scannedSeq, t)) {

					TaggedFrequency tFreq = new BasicTaggedFrequency();
					tFreq.setTags(this.tags);
					setScanned(this.scannedSeq, t);
					tFreq.addValue(t);

					List<Future<String>> resList = new ArrayList<Future<String>>();
					BufferedReader br2;
					for (int m = i; m < this.pathList.size(); m++) {
						String t1 = ((Path) this.pathList.get(m)).getFileName()
								.toString();
						br2 = Files.newBufferedReader(this.pathList.get(m),
								Charset.defaultCharset());
						Worker w = new Worker(t1, s1, br2, tFreq);
						if (this.pathList.get(i) == this.pathList.get(m)) {
							w.jumpTo(this.scannedSeq);
						}
						resList.add(sendFuture(w));
					}

					StringBuffer ids = new StringBuffer();
					ids.append(s1.getId() + "\t" + s1.getSequence());
					ids.append(System.lineSeparator());
					for (Future<String> f : resList) {
						try {
							ids.append((String) f.get());
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}
					writeId(ids.toString(), idWriter);
					writeSequence(s1, wr);
					AbstractMap.SimpleEntry<Sequence, TaggedFrequency> entry = new AbstractMap.SimpleEntry<Sequence, TaggedFrequency>(
							s1, tFreq);
					tableWriter.write(entry, header);
					header = false;
				}
			}
			clearTag(t);
		}
	}

	private synchronized boolean alredyScanned(long num, String tag) {
		long c = num;
		return this.scannedMap.get(tag).contains(Long.valueOf(c));
	}

	private synchronized void setScanned(long num, String tag) {
		this.scannedMap.get(tag).add(new Long(num));
	}

	private synchronized void clearTag(String tag) {
		this.scannedMap.get(tag).clear();
	}

	private FutureTask<String> sendFuture(Worker w) {
		try {
			this.semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FutureTask<String> f = new FutureTask<String>(w);
		new Thread(f).start();
		return f;
	}

	private void chopLower(String tag, long cutoff) {
		Collections.sort(this.scannedMap.get(tag));
		List<Long> res = null;
		for (int i = 0; i < this.scannedMap.get(tag).size(); i++) {
			long l = this.scannedMap.get(tag).get(i);
			if ((l >= cutoff) && (i != 0)) {
				res = new ArrayList<Long>(this.scannedMap.get(tag).subList(i,
						this.scannedMap.get(tag).size()));
				this.scannedMap.put(tag, res);
				break;
			}
		}
	}

	protected void incrementProgress() {
	}

	class Worker implements Callable<String> {
		private String tag = null;
		private Sequence s = null;
		private TaggedFrequency tFreq = null;
		private BufferedReader br = null;
		private SequenceReader reader = null;
		private long progress;

		public Worker(String tag, Sequence s, BufferedReader br,
				TaggedFrequency tFreq) {
			this.tag = tag;
			this.s = s;
			this.tFreq = tFreq;
			this.br = br;
			this.reader = new FastaSequenceReader(br);
			this.progress = 0L;
		}

		private synchronized void addTag(String t) {
			this.tFreq.addValue(t);
		}

		public void jumpTo(long num) {
			while ((this.reader.hasNext()) && (this.progress < num)) {
				this.reader.nextSequence(false);
				this.progress += 1L;
			}
			FileStreamCounter.this.chopLower(this.tag, this.progress);
		}

		public String call() throws Exception {
			StringBuffer ids = new StringBuffer();
			while (this.reader.hasNext()) {
				Sequence s1 = this.reader.nextSequence(true);
				this.progress += 1L;
				if (!FileStreamCounter.this.alredyScanned(this.progress,
						this.tag)) {

					if (this.s.compareTo(s1) == 0) {
						FileStreamCounter.this.setScanned(this.progress,
								this.tag);
						addTag(this.tag);
						ids.append("\t" + s1.getId());
						ids.append(System.lineSeparator());
					}
				}
			}
			this.br.close();
			FileStreamCounter.this.semaphore.release();
			return ids.toString();
		}
	}
}