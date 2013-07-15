package bacci.giovanni.deunifier.DeUniFier.io;

import java.io.BufferedReader;
import java.util.Map.Entry;

import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;

public abstract class FrequencyReader {

	protected String sep = null;
	protected BufferedReader reader = null;
	protected boolean header = true;
	
	public FrequencyReader(String sep, BufferedReader reader, boolean header) {
		super();
		this.sep = sep;
		this.reader = reader;
		this.header = header;
	}
	
	public abstract Entry<Sequence, TaggedFrequency> readNextFrequency();
	
}
