package bacci.giovanni.deunifier.DeUniFier.io;

import java.util.Scanner;

public class FastaSequenceScanner {
	private Scanner scan = null;

	private static final char FASTA_SEP = '>';

	public static final int ID = 0;

	public static final int SEQ = 1;

	public FastaSequenceScanner(Scanner scan) {
		this.scan = scan;
		this.scan.useDelimiter(String.valueOf(FASTA_SEP));
	}

	public String[] nextSeq() {
		StringBuffer buffer = new StringBuffer();
		String[] splitted = this.scan.next().split(System.lineSeparator());
		for (int i = 1; i < splitted.length; i++) {
			buffer.append(splitted[i].trim().intern());
		}
		return new String[] { splitted[0].intern(), buffer.toString().intern() };
	}

	public boolean hasNextSeq() {
		return this.scan.hasNext();
	}
}