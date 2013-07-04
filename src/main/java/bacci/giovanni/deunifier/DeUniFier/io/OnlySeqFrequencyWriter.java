package bacci.giovanni.deunifier.DeUniFier.io;

import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map.Entry;

public class OnlySeqFrequencyWriter extends FrequencyWriter {
	protected OnlySeqFrequencyWriter(String sep, BufferedWriter writer) {
		super(sep, writer);
	}

	public void write(Entry<Sequence, TaggedFrequency> freqs, boolean header) {
		try {
			String seq = freqs.getKey().getSequence();
			String fr = freqs.getValue().getTabbedString(this.sep, false);
			if (header) {
				String head = "Sequence" + this.sep
						+ freqs.getValue().getTabbedString(this.sep, true);
				this.writer.write(head);
				this.writer.newLine();
				this.writer.flush();
			}
			String str = String.format("%s%s%s", seq, this.sep, fr);
			this.writer.write(str);
			this.writer.newLine();
			this.writer.flush();
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}