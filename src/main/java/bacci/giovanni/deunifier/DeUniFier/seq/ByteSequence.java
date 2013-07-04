package bacci.giovanni.deunifier.DeUniFier.seq;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ByteSequence implements Sequence {
	private static final long serialVersionUID = -399139102464674543L;
	private byte[] sequence = null;
	private byte[] id = null;
	private final String ENCODING = "UTF-8";

	public ByteSequence(String sequence, String id) {
		try {
			this.sequence = sequence.trim().toLowerCase().getBytes(ENCODING);
			this.id = id.trim().getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding: " + ENCODING);
		}
	}

	public ByteSequence(String sequence) {
		try {
			this.sequence = sequence.trim().toLowerCase().getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding: " + ENCODING);
		}
	}

	public int compareTo(Sequence o) {
		return getSequence().compareTo(o.getSequence());
	}

	public String getSequence() {
		String res = null;
		try {
			res = new String(this.sequence, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return res;
	}

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

	public Byte[] getByteSeq() {
		Byte[] arr = new Byte[this.sequence.length];
		for (int i = 0; i < this.sequence.length; i++) {
			arr[i] = Byte.valueOf(this.sequence[i]);
		}

		return arr;
	}

	public Map<Attributes, Object> getAttributes() {
		return null;
	}
}
