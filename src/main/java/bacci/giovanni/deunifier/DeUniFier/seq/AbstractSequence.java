package bacci.giovanni.deunifier.DeUniFier.seq;

import java.util.Map;

/**
 * Standard implemetation of a sequence.
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 * 
 */

public abstract class AbstractSequence implements Sequence {
	private static final long serialVersionUID = -8036608080162365045L;
	private String sequence = null;
	private String id = null;

	/**
	 * Contructor.
	 * 
	 * @param sequence
	 *            a sequence as a string
	 * @param id
	 *            an id as a String
	 */
	public AbstractSequence(String sequence, String id) {
		this.sequence = sequence;
		this.id = id;
	}

	/**
	 * Returns the Sequence as a {@link String}. Sequence is returned as it was
	 * inserted in the constructor.
	 */
	public String getSequence() {
		return this.sequence;
	}

	/**
	 * Returns the id as a {@link String}
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Print the id and the sequence.
	 */
	public void print() {
		System.out.println(this.id);
		System.out.println(this.sequence);
	}

	/**
	 * Abstract method. This method returns all sequence's attributes
	 * or null if the sequence has no attributes.
	 */
	public abstract Map<Attributes, Object> getAttributes();

	/**
	 * A {@link String} with the id and the sequence
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.id + System.lineSeparator());
		buffer.append(this.sequence + System.lineSeparator());
		return buffer.toString();
	}
}