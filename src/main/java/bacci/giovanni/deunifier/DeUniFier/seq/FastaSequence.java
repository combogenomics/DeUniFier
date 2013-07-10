package bacci.giovanni.deunifier.DeUniFier.seq;

import java.util.HashMap;
import java.util.Map;

/**
 * Fasta implementation of an {@link AbstractSequence}.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class FastaSequence extends AbstractSequence {
	private static final long serialVersionUID = 1670711603764418070L;
	private String fileName = null;

	/**
	 * @see AbstractSequence#AbstractSequence(String, String)
	 * @param sequence
	 * @param id
	 */
	public FastaSequence(String sequence, String id) {
		super(sequence, id);
	}

	/**
	 * @see AbstractSequence#getAttributes()
	 */
	public Map<Attributes, Object> getAttributes() {
		Map<Attributes, Object> attributes = new HashMap<Attributes, Object>();
		attributes.put(Attributes.FILE_NAME, this.fileName);
		return attributes;
	}

	/**
	 * @see String#compareTo(String)
	 */
	public int compareTo(Sequence o) {
		return getSequence().compareTo(o.getSequence());
	}

	/**
	 * Method that set the file name of this fasta sequence.
	 * @param fileName the name of the file from wich this sequence came from
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}