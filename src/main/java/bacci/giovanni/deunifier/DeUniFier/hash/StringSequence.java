package bacci.giovanni.deunifier.DeUniFier.hash;

/**
 * Utility class to store id and sequence in two separate string.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class StringSequence {
	
	private String id = null;
	private String seq = null;
	
	/**
	 * Constructor
	 * @param id the id
	 * @param seq the sequence
	 */
	public StringSequence(String id, String seq) {
		this.id = id;
		this.seq = seq;
	}

	/**
	 * Return the complete id of the sequence
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Return the sequence
	 * @return the sequence
	 */
	public String getSeq() {
		return seq;
	}
		
}
