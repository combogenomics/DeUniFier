package bacci.giovanni.deunifier.DeUniFier.io;

import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map.Entry;

public class SimpleFrequencyWriter extends FrequencyWriter {
	private OnlySeqFrequencyWriter seqWriter = null;

	public SimpleFrequencyWriter(String sep, BufferedWriter writer) {
		super(sep, writer);
		this.seqWriter = new OnlySeqFrequencyWriter(sep, writer);
	}

	public void write(Entry<Sequence, TaggedFrequency> freqs, boolean header) {
		try {
			if (freqs.getKey().getId() != null) {
				String id = freqs.getKey().getId();
				String seq = freqs.getKey().getSequence();
				String fr = freqs.getValue().getTabbedString(this.sep, false);
				if (header) {
					String head = "Id" + this.sep + "Sequence" + this.sep
							+ freqs.getValue().getTabbedString(this.sep, true);
					this.writer.write(head);
					this.writer.newLine();
					this.writer.flush();
				}
				String str = String.format("%s%s%s%s%s", id, this.sep, seq,
						this.sep, fr);
				this.writer.write(str);
				this.writer.newLine();
				this.writer.flush();
			} else {
				this.seqWriter.write(freqs, header);
			}
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}