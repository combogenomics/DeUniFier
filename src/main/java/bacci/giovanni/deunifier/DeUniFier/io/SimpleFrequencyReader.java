package bacci.giovanni.deunifier.DeUniFier.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map.Entry;

import bacci.giovanni.deunifier.DeUniFier.freq.BasicTaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.seq.ByteSequence;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;

public class SimpleFrequencyReader extends FrequencyReader {

	private static final int ID_INDEX = 0;
	private static final int SEQ_INDEX = 1;
	private static final int FREQ_BEGIN_INDEX = 2;
	private String[] tags = null;

	public SimpleFrequencyReader(String sep, BufferedReader reader,
			boolean header) {
		super(sep, reader, header);
	}

	@Override
	public Entry<Sequence, TaggedFrequency> readNextFrequency() {
		String line = null;
		Entry<Sequence, TaggedFrequency> entry = null;
		try {
			if (header) {
				if ((line = reader.readLine()) != null) {
					String[] ass = Arrays.copyOfRange(line.split(sep),
							FREQ_BEGIN_INDEX, line.split(sep).length);
					tags = new String[ass.length];
					for (int i = 0; i < ass.length; i++) {
						tags[i] = ass[i];
					}
				}
				header = false;
			}
			if ((line = reader.readLine()) != null) {
				Sequence s = new ByteSequence(getSeq(line), getId(line));
				TaggedFrequency freq = getFreq(line);
				entry = new AbstractMap.SimpleEntry<Sequence, TaggedFrequency>(s, freq);
			}			
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
		return entry;
	}

	private String getId(String line) {
		return line.split(sep)[ID_INDEX];
	}

	private String getSeq(String line) {
		return line.split(sep)[SEQ_INDEX];
	}

	private TaggedFrequency getFreq(String line) {
		String[] ass = Arrays.copyOfRange(line.split(sep), FREQ_BEGIN_INDEX,
				line.split(sep).length);
		TaggedFrequency freq = new BasicTaggedFrequency();
		if (tags == null) {
			tags = new String[ass.length];
			for (int i = 1; i <= ass.length; i++) {
				tags[i] = String.valueOf(i);
			}
		}
		freq.setTags(tags);
		for (int i = 0; i < ass.length; i++) {
			long l = Long.valueOf(ass[i]);
			freq.setValue(tags[i], l);
		}
		return freq;
	}

}
