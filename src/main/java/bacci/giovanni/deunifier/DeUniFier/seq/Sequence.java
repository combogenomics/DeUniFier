package bacci.giovanni.deunifier.DeUniFier.seq;

import java.awt.RenderingHints.Key;
import java.io.Serializable;
import java.util.Map;

/**
 * An Object that able to store a Sequence and an id.
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 * 
 */
public abstract interface Sequence extends Comparable<Sequence>, Serializable {
	/**
	 * Returns the sequence as a {@link String}.
	 * 
	 * @return the sequence
	 */
	public abstract String getSequence();

	/**
	 * Returns the id as a {@link String}.
	 * 
	 * @return the id
	 */
	public abstract String getId();

	/**
	 * Returns all sequence's {@link Attributes} or null if the sequence has no
	 * {@link Attributes}.
	 * 
	 * @return a {@link Map} with {@link Key} {@link Attributes}.
	 */
	public abstract Map<Attributes, Object> getAttributes();
}