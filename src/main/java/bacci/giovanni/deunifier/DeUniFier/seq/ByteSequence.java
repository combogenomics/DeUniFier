package bacci.giovanni.deunifier.DeUniFier.seq;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * {@link Byte} implememtation of the {@link Sequence} interface. Here Sequence
 * and id are stored as a byte array. Strinngs and byte arrays are ancoded using 
 * standard UTF-8 format. 
 * @see String#getBytes(Charset)
 * @see String#String(byte[], Charset)
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 * 
 */
public class ByteSequence implements Sequence {
	private static final long serialVersionUID = -399139102464674543L;
	private byte[] sequence = null;
	private byte[] id = null;
	private final String ENCODING = "UTF-8";

	/**
	 * Constructor.
	 * @param sequence the sequence
	 * @param id the id
	 */
	public ByteSequence(String sequence, String id) {
		try {
			this.sequence = sequence.trim().toLowerCase().getBytes(ENCODING);
			this.id = id.trim().getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding: " + ENCODING);
		}
	}

	/**
	 * Constructor that build a Sequence without id.
	 * @param sequence the sequence
	 */
	public ByteSequence(String sequence) {
		try {
			this.sequence = sequence.trim().toLowerCase().getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding: " + ENCODING);
		}
	}

	/**
	 * This method will compare this sequence to another. The two sequences will be compared lexicographically.
	 * @see String#compareTo(String)
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(Sequence o) {
		return getSequence().compareTo(o.getSequence());
	}

	/**
	 * @see Sequence#getSequence()	
	 */
	public String getSequence() {
		String res = null;
		try {
			res = new String(this.sequence, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * @see Sequence#getId()
	 */
	public String getId() {
		String res = null;
		if (this.id == null)
			return null;
		try {
			res = new String(this.id, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Returns the sequence as a {@link Byte[]} 
	 * @return a {@link Byte[]}
	 */
	public Byte[] getByteSeq() {
		Byte[] arr = new Byte[this.sequence.length];
		for (int i = 0; i < this.sequence.length; i++) {
			arr[i] = Byte.valueOf(this.sequence[i]);
		}

		return arr;
	}

	/**
	 * @see Sequence#getAttributes()
	 */
	public Map<Attributes, Object> getAttributes() {
		return null;
	}
}
