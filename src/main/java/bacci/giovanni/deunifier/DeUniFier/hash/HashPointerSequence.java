package bacci.giovanni.deunifier.DeUniFier.hash;

import java.io.RandomAccessFile;

/**
 * This class convert a sequence into his hash code and store its value as an attribute.
 * This class store the pointer to that sequence in a {@link RandomAccessFile} instance too.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class HashPointerSequence implements PointerSequence<Integer> {

	private int hashSequence;
	private long pointer;
	
	/**
	 * Constructor.
	 * @param sequence the sequence
	 * @param pointer the pointer
	 */
	public HashPointerSequence(String sequence, long pointer){
		this.hashSequence = sequence.hashCode();
		this.pointer = pointer;
	}	
	
	public long getPointer() {		
		return pointer;
	}

	public Integer getSequence() {
		return hashSequence;
	}

	public int compareTo(PointerSequence<Integer> o) {
		if(o instanceof HashPointerSequence){
			if(this.getSequence() == o.getSequence()){
				return 0;
			}else{
			    return 1;
			}
		}else{
			throw new IllegalArgumentException("Cannot compare Hash sequence with other sequences");
		}
	}

}
