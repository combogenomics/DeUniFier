package bacci.giovanni.deunifier.DeUniFier.io;

import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.BufferedWriter;
import java.io.IOException;

public class FastaSequenceWriter implements SequenceWriter {
	private BufferedWriter writer = null;
	private static final String FASTA_SEP = ">";

	public FastaSequenceWriter(BufferedWriter writer) {
		this.writer = writer;
	}

	public void writeSequence(Sequence sequence) {
		String seq = sequence.getSequence().trim().toUpperCase().intern();
		String id = sequence.getId().trim().intern();
		if (seq.length() > 70) {
			StringBuffer buffer = new StringBuffer();
			int pieces = seq.length() / 70;
			int rest = seq.length() % 70;
			for (int i = 0; i < pieces; i++) {
				int begin = i * 70;
				int end = begin + 70;
				buffer.append(seq.substring(begin, end)
						+ System.lineSeparator());
			}
			buffer.append(seq.substring(seq.length() - rest));
			seq = new String(buffer.toString().intern());
		}
		String seqString = String.format("%1$s%2$s%n%3$s%n", new Object[] {
				FASTA_SEP, id, seq });
		try {
			this.writer.write(seqString);
			this.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}