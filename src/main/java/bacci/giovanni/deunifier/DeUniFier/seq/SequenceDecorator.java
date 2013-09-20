package bacci.giovanni.deunifier.DeUniFier.seq;

import java.util.Map;

public abstract class SequenceDecorator implements Sequence {
	
	private static final long serialVersionUID = 1L;
	private Sequence seq = null;
	
	public SequenceDecorator(Sequence seq) {
		this.seq = seq;
	}

	public int compareTo(Sequence o) {
		return getSequence().compareTo(o.getSequence());
	}

	public String getSequence() {
		return getSequence();
	}

	public Map<Attributes, Object> getAttributes() {
		return null;
	}

	public String getId() {
		return null;
	}
	
	
	
}
