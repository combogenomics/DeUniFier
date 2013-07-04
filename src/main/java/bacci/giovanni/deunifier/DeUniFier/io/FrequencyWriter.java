package bacci.giovanni.deunifier.DeUniFier.io;

import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.BufferedWriter;
import java.util.Map.Entry;

public abstract class FrequencyWriter {
	protected String sep = null;
	protected BufferedWriter writer = null;

	public FrequencyWriter(String sep, BufferedWriter writer) {
		this.sep = sep;
		this.writer = writer;
	}

	public abstract void write(Entry<Sequence, TaggedFrequency> entry,
			boolean header);
}