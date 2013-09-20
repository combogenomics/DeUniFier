package bacci.giovanni.deunifier.DeUniFier.hash;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Wrapper for a {@link RandomAccessFile}. This class is able to recover sequences
 * stored in a fasta file.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class FastaHashRandomAccessFile extends
		AbstractRandomAccessFileSequences {

	private final static String FASTA_SEPARATOR = ">";

	public FastaHashRandomAccessFile(RandomAccessFile raf, String name) {
		super(raf, name);
	}

	public PointerSequence<Integer> nextSequence() throws IOException {
		String line = null;
		long point = raf.getFilePointer();
		long endPointer = point;
		StringBuffer buffer = null;
		boolean begin = false;
		while ((line = raf.readLine()) != null) {
			if ((line.startsWith(FASTA_SEPARATOR)) && (begin == false)) {
				begin = true;
				buffer = new StringBuffer();
				endPointer = raf.getFilePointer();
				continue;
			} else if ((line.startsWith(FASTA_SEPARATOR)) && (begin == true)) {				
				raf.seek(endPointer);
				break;
			}			
			if (!begin) {
				point = raf.getFilePointer();
			} else {
				buffer.append(line);
				endPointer = raf.getFilePointer();
			}
		}
		if (buffer == null) {
			return null;
		} else {
			return new HashPointerSequence(buffer.toString()
					.replaceAll("\\s", "").toLowerCase(), point);
		}
	}

	public PointerSequence<Integer> getSequence(long pointer)
			throws IOException {
		long oldPointer = raf.getFilePointer();
		raf.seek(pointer);
		PointerSequence<Integer> s = nextSequence();
		raf.seek(oldPointer);
		return s;
	}

	public StringSequence getStringSequence(long pointer) throws IOException {
		long oldPointer = raf.getFilePointer();
		raf.seek(pointer);
		String line = null;
		StringBuffer buffer = null;
		String id = null;
		StringSequence s = null;
		boolean begin = false;
		while ((line = raf.readLine()) != null) {
			if ((line.startsWith(FASTA_SEPARATOR)) && (begin == false)) {
				begin = true;
				buffer = new StringBuffer();
				id = line;
				continue;
			} else if ((line.startsWith(FASTA_SEPARATOR)) && (begin == true)) {
				break;
			}
			if (begin) {
				buffer.append(line);
			}
		}
		if (buffer == null) {
			raf.seek(oldPointer);
			return null;
		} else {
			s = new StringSequence(id, buffer.toString().replaceAll("\\s", "").toLowerCase());
			raf.seek(oldPointer);
			return s;
		}
	}

}
