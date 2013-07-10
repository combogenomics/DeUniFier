package bacci.giovanni.deunifier.DeUniFier.pipelines.unifier;

import bacci.giovanni.deunifier.DeUniFier.freq.BasicFrequencyTag;
import bacci.giovanni.deunifier.DeUniFier.freq.FrequencyTags;
import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.io.FastaSequenceReader;
import bacci.giovanni.deunifier.DeUniFier.io.FrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SequenceReader;
import bacci.giovanni.deunifier.DeUniFier.io.SequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.seq.ByteSequence;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observer;

/**
 * Implementation of a {@link FileCounter}. This class reads a sequences' file in memory and
 * check only for identical sequences. With this class no id will be reported and the ids 
 * will be written with new unique names.
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class SequenceFileCounter extends FileCounter {
	private FrequencyTags freq = null;

	private long uniques;

	/**
	 * @see FileCounter#FileCounter(List)
	 * @param list path list
	 */
	public SequenceFileCounter(List<Path> list) {
		super(list);
		String[] tags = new String[list.size()];
		for (int i = 0; i < tags.length; i++) {
			tags[i] = ((Path) list.get(i)).getFileName().toString();
		}
		this.freq = new BasicFrequencyTag(tags);
		this.uniques = 0L;
	}

	/**
	 * Method called every time that a {@link Sequence} is scanned. This method send a message
	 * to all {@link Observer} using a double parameter that ranges from 0.0 to 1.0 where 0.0 
	 * means that no progress have been done and 1.0 means that the pipeline is finished.
	 */
	protected void incrementProgress() {
		this.scannedSeq += 1L;
		double prog = 100.0D - (this.uniques - this.scannedSeq)
				* (100.0D / this.uniques);
		setChanged();
		notifyObservers(Double.valueOf(prog));
	}

	/**
	 * This method will not write the ids so the {@link Writer} parameter
	 * could be <code>null</code>
	 * @see FileCounter#writeOutput(SequenceWriter, FrequencyWriter, Writer).
	 */
	public void writeOutput(SequenceWriter wr, FrequencyWriter tableWriter,
			Writer idWriter) throws IOException {
		this.scannedSeq = 0L;
		setDoing("Searching for redundant sequences:");
		for (Path p : this.pathList) {
			setDoing(p.toString());
			BufferedReader br = Files.newBufferedReader(p,
					Charset.defaultCharset());
			SequenceReader reader = new FastaSequenceReader(br);
			String tag = p.getFileName().toString();
			while (reader.hasNext()) {
				this.freq.addSequence(reader.nextSequence(false), tag);
				this.numSeq += 1L;
			}
			br.close();
		}
		setDoing("Done:");
		this.uniques = this.freq.getUniqueCount();
		setDoing("Found: " + this.uniques + " unique sequences on "
				+ this.numSeq + " total sequences");
		double rate = (this.numSeq - this.uniques) * (100.0D / this.numSeq);
		String res = String.format("%.2f%s of sequences were redundant",
				new Object[] { Double.valueOf(rate), "%" });
		setDoing(res);
		setDoing("Begin writing outputs...");
		int deep = Long.toString(this.uniques).length();
		Iterator<Entry<Sequence, TaggedFrequency>> it = this.freq
				.getEntrySetIterator();
		boolean header = true;
		
		while (it.hasNext()) {
			incrementProgress();
			Entry<Sequence, TaggedFrequency> entry = it.next();
			String id = String.format("unique_sequence.%0" + deep + "d",
					new Long(this.scannedSeq));
			Sequence s = new ByteSequence(entry.getKey().getSequence(), id);
			entry = new AbstractMap.SimpleEntry<Sequence, TaggedFrequency>(s, entry.getValue());
			wr.writeSequence(s);
			tableWriter.write(entry, header);
			header = false;
		}
	}
}