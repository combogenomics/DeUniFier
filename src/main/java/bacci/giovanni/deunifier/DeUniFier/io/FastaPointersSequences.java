package bacci.giovanni.deunifier.DeUniFier.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import bacci.giovanni.deunifier.DeUniFier.seq.ByteSequence;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;

/**
 * This class reads a Fasta sequences file using a {@link RandomAccessFile}.
 * Each sequence pointer is stored in a {@link List}.
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 * 
 */
public class FastaPointersSequences {
	private RandomAccessFile raf = null;
	private List<Long> pointers = null;
	private Path input = null;

	/**
	 * Constructor.
	 * 
	 * @param input
	 *            path to the input fasta file
	 * @throws IOException
	 *             if there is an error reading the file
	 */
	public FastaPointersSequences(Path input) throws IOException {
		this.raf = new RandomAccessFile(input.toFile(), "r");
		this.input = input;
		this.pointers = new ArrayList<Long>();
		//this.scanning();
	}

	/**
	 * Private method that scans a fasta file and collect all the pointers to
	 * the sequences
	 * 
	 * @throws IOException
	 *             if the file cannot be read
	 */
	private void scanning() throws IOException {
		String line = null;
		long point = 0;
		while ((line = raf.readLine()) != null) {
			if (line.startsWith(">")) {
				pointers.add(point);
			} else {
				point = raf.getFilePointer();
			}
		}
		pointers.add(raf.getFilePointer());
	}
	
	/**
	 * Return the specified sequence as a {@link ByteSequence}. The index has to be an integer between 0 and {@link FastaPointersSequences#size()}
	 * otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param index index of the sequence 
 	 * @return the sequence as a {@link ByteSequence}
	 * @throws IOException if there i an error reading in the file
	 */
	public Sequence get(int index) throws IOException{
		raf.seek(pointers.get(index));
		String line = null;
		String id = null;
		StringBuffer seq = new StringBuffer();
		while(raf.getFilePointer() < pointers.get(index + 1)){
			line = raf.readLine();
			if((line.startsWith(">")) && (id == null)){
				id = line.substring(1).trim();
				continue;
			}else if((!line.startsWith(">")) && (id != null)){
				seq.append(line);
				continue;
			}else if(((line.startsWith(">")) && (id != null)) || ((!line.startsWith(">")) && (id == null))){
				throw new IllegalArgumentException("Sequence number " + index+1 + " is bad formatted");
			}
		}
		return new ByteSequence(seq.toString().replaceAll("\\s", "").toLowerCase(), id);
	}	
	
	/**
	 * Method that return the number of sequences in this file
	 * @return the number of sequences in the file
	 */
	public int size(){
		return pointers.size() - 1;
	}

}
