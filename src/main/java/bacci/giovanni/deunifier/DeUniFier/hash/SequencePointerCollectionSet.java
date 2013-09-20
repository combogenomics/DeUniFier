package bacci.giovanni.deunifier.DeUniFier.hash;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * An object that store {@link SequencePointerCollection} objects. Each instance stored is
 * associated with a generic type <code>T</code> that is the type returned by the {@link PointerSequence}
 * added. Ideally this object has to contain only one instance of the same sequence.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 * @param <T> the type returned by the {@link PointerSequence#getSequence()} method
 */
public interface SequencePointerCollectionSet<T> {
	
	/**
	 * Return a {@link SequencePointerCollection} associated to the param <code>t</code>
	 * @param t the sequence returned by {@link PointerSequence#getSequence()}
	 * @return a {@link SequencePointerCollection}
	 */
	public SequencePointerCollection getCollection(T t);
	
	/**
	 * Add an entry to this Object.
	 * @param sequence the sequence to add
	 * @param araf the {@link AbstractRandomAccessFileSequences} that points to the sequence
	 */
	public void addEntry(PointerSequence<T> sequence, AbstractRandomAccessFileSequences araf);
	
	/**
	 * This method returns an {@link Iterator} over each entry stored in this object.
	 * @return an {@link Iterator} over each entry stored in this object
	 */
	public Iterator<Entry<T, SequencePointerCollection>> entrySetIterator();
	
	/**
	 * This method return all the {@link AbstractRandomAccessFileSequences} previously added.
	 * @return a {@link List} of {@link AbstractRandomAccessFileSequences}
	 */
	public List<AbstractRandomAccessFileSequences> getKeys();
	
	/**
	 * This method returns the count of unique sequences of type <code>T</code>
	 * @return the number of unique sequences
	 */
	public long uniquesCount();
	
}
