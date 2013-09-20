package bacci.giovanni.deunifier.DeUniFier.hash;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

/**
 * An object that store entries of {@link AbstractRandomAccessFileSequences} and pointer toi the file.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public interface SequencePointerCollection {
	
	/**
	 * Return an iterator over the entries stored
	 * @return an {@link Iterator}
	 */
	public Iterator<Entry<AbstractRandomAccessFileSequences, List<Long>>> entrySetIterator();
	
	/**
	 * Add an entry.
	 * @param araf the {@link AbstractRandomAccessFileSequences}
	 * @param pointer the pointer
	 */
	public void addEntry(AbstractRandomAccessFileSequences araf, long pointer);
	
	/**
	 * Return all the key as a {@link Set} of {@link AbstractRandomAccessFileSequences}
	 * @return a {@link Set} of {@link AbstractRandomAccessFileSequences} previously added
	 */
	public Set<AbstractRandomAccessFileSequences> keySet();
}
