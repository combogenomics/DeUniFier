package bacci.giovanni.deunifier.DeUniFier.io;

import bacci.giovanni.deunifier.DeUniFier.seq.ByteSequence;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.BufferedReader;
import java.io.IOException;

public class FastaSequenceReader implements SequenceReader {
	private BufferedReader br = null;
	private static final String FASTA_SEP = ">";
	private String line = null;
	private boolean finish = false;
	private boolean started = false;
	private long fileLine;

	public FastaSequenceReader(BufferedReader br) {
		this.br = br;
		this.fileLine = 0L;
	}

	public Sequence nextSequence(boolean hasId) {
		if (!this.started) {
			nextLine();
			this.started = true;
		}
		StringBuffer sequence = new StringBuffer();
		String id = null;
		boolean newSeq = false;
		while (!this.finish) {
			if (!this.line.isEmpty()) {
				if (this.line.startsWith(FASTA_SEP)) {
					if (newSeq)
						break;
					id = this.line.substring(1).trim().intern();
					newSeq = true;
					nextLine();

				} else {
					if (!newSeq) {
						throw new IllegalArgumentException(
								"Trying to parse sequence without id at line "
										+ this.fileLine);
					}
					sequence.append(this.line.toLowerCase());
					nextLine();
				}
			} else {
				nextLine();
			}
		}
		if (sequence.length() == 0) {
			throw new IllegalArgumentException(
					"Trying to parse sequence with length 0 at line "
							+ this.fileLine);
		}
		Sequence seq = null;
		if (hasId) {
			seq = new ByteSequence(sequence.toString().replaceAll("\\s", "")
					.intern(), id);
		} else {
			seq = new ByteSequence(sequence.toString().replaceAll("\\s", "")
					.intern());
		}
		return seq;
	}

	private void nextLine() {
		try {
			this.line = this.br.readLine();
			this.fileLine += 1L;
			if (this.line == null) {
				this.finish = true;
				this.br.close();
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("BufferedReader error");
		}
	}

	public boolean hasNext() {
		return !this.finish;
	}
}