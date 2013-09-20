package bacci.giovanni.deunifier.DeUniFier.hash;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Abstract class. Implementations of this class have to be able to recover sequences from a 
 * Sequence file using the {@link RandomAccessFile} field. 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractRandomAccessFileSequences {
	protected RandomAccessFile raf = null;
	private String name = null;
	
	public AbstractRandomAccessFileSequences(RandomAccessFile raf, String name){
		this.raf = raf;
		this.name = name;
	}
	
	/**
	 * Return the next sequence in the file
	 * @return the next sequence in the file
	 * @throws IOException if an IO Error occurs reading in the file
	 */
	public abstract PointerSequence nextSequence() throws IOException;
	
	/**
	 * Return the sequence specified by the pointer parameter and restore the original position
	 * of the pointer.
	 * @param pointer the position in the file 
	 * @return the sequence
	 * @throws IOException if an IO Error occurs reading in the file
	 */
	public abstract PointerSequence getSequence(long pointer) throws IOException;
	
	/**
	 * Return the sequence as an instance of {@link StringSequence} in order to write it in a file or display it 
	 * in a console.
	 * @param pointer the sequence position in the file
	 * @return the sequence as a {@link StringSequence} instance
	 * @throws IOException if an IO Error occurs reading in the file
	 */
	public abstract StringSequence getStringSequence(long pointer) throws IOException;

	
	/**
	 * Return the name associated with this {@link AbstractRandomAccessFileSequences}
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
