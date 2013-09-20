package bacci.giovanni.deunifier.DeUniFier.pipelines.unifier;

import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.io.FrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

/**
 * Standard implementation of a pipeline able to find all identitcal sequences
 * in a file and write:<br>
 * 1 - a frequency table<br>
 * 2 - a file containing all unique sequences<br>
 * 3 - (optional) a file containing all ids<br>
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 * 
 */
public abstract class FileCounter extends Observable {
	protected List<Path> pathList = null;
	protected long numSeq;
	protected long scannedSeq;

	/**
	 * Constructor.
	 * 
	 * @param list
	 *            a list of sequences' files.
	 */
	// TODO Check if the pathlist contains directories
	public FileCounter(List<Path> list) {
		this.pathList = list;
		this.numSeq = 0L;
		this.scannedSeq = 0L;
	}

	/**
	 * Utility method to write sequences in a synchronized way
	 * 
	 * @param s
	 *            the {@link Sequence} to write
	 * @param wr
	 *            a {@link SequenceWriter}
	 */
	protected synchronized void writeSequence(Sequence s, SequenceWriter wr) {
		wr.writeSequence(s);
		notifyAll();
	}

	/**
	 * Utility method to write ids in a synchronized way
	 * 
	 * @param id
	 *            the id.
	 * @param writer
	 *            a {@link Writer}
	 * @throws IOException
	 *             if an {@link IOException} occurs
	 */
	protected synchronized void writeId(String id, Writer writer)
			throws IOException {
		writer.write(id);
		writer.write(System.lineSeparator());
		writer.flush();
		notifyAll();
	}

	/**
	 * Utility method to write an entry in the frequency table in a synchronized
	 * way. This method return a {@link Boolean} always set to
	 * <code>false</code> in order to write the frequency table's header only
	 * one time. For example
	 * <p>
	 * <code>
	 * boolean header = true;
	 * for(Sequence s : listSequences){
	 * 	header = hypotheticalFileCounter.writeTable(entry, writer, header) 
	 * }
	 * </code>
	 * </p>
	 * In this way the header will be written only one time.
	 * 
	 * @param entry
	 *            an {@link Entry} with a {@link Sequence} as {@link Key} and a
	 *            {@link FrequencyWriter} as value.
	 * @param writer
	 *            a {@link FrequencyWriter}
	 * @param header
	 *            a {@link Boolean}
	 * @return <code>false</code>
	 */
	protected synchronized boolean writeTable(
			Entry<Sequence, TaggedFrequency> entry, FrequencyWriter writer,
			boolean header) {
		writer.write(entry, header);
		notifyAll();
		return false;
	}

	/**
	 * Observable method.
	 * 
	 * @see Observable
	 * @param doing
	 *            a String that will be passed to all the {@link Observer}
	 */
	protected void setDoing(String doing) {
		setChanged();
		notifyObservers(doing);
	}

	/**
	 * Abstract method. This method should be used to tip off all observers that
	 * this FileCounter advanced.
	 */
	protected abstract void incrementProgress();

	/**
	 * Abstract method that has to write a sequence, its frequency and the ids
	 * 
	 * @param sequenceWriter
	 *            a {@link SequenceWriter}
	 * @param frequencyWriter
	 *            a {@link FrequencyWriter}
	 * @param writer
	 *            a {@link Writer}
	 * @throws IOException
	 *             if an {@link IOException} occurs while writing.
	 */
	public abstract void writeOutput(SequenceWriter sequenceWriter,
			FrequencyWriter frequencyWriter, Writer writer) throws IOException;
}