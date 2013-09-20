package bacci.giovanni.deunifier.DeUniFier.hash;

import java.io.RandomAccessFile;

/**
 * A sequence that store only a pointer value. Classes implementing this interface have to 
 * use a {@link RandomAccessFile} in order to recover the sequence using the {@link RandomAccessFile#seek(long)}
 * method with {@link PointerSequence#getPointer()}.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 * @param <T> the returning sequence type
 */
public interface PointerSequence<T> extends Comparable<PointerSequence<T>> {
	/**
	 * Return the file-pointer offset where this sequence begin. 
	 * @return the file-pointer offset
	 */
	public long getPointer();
	
	/**
	 * Return the sequence.
	 * @return the sequence
	 */
	public T getSequence();
}
