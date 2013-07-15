package bacci.giovanni.deunifier.DeUniFier.freq;

import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Object able to keep a frequency table of sequences and tags.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public interface FrequencyTags extends Serializable {

	/**
	 * Add a sequence and a tag. 
	 * @param sequence the {@link Sequence}
	 * @param tag a tag {@link String}
	 */
	public void addSequence(Sequence sequence, String tag);

	/**
	 * Returns a sequence occurrence with a specified tag.
	 * @param sequence the sequence.
	 * @param tag the tag.
	 * @return a {@link Long}
	 */
	public long getCount(Sequence sequence, String tag);

	/**
	 * Returns all sequences as a {@link Collection}
	 * @return all sequences previously added to this {@link FrequencyTags}
	 */
	public Collection<Sequence> getSequences();

	/**
	 * Returns all tags as a {@link Collection}
	 * @return all tags previously added to this {@link FrequencyTags}
	 */
	public Collection<String> getTags();

	/**
	 * Returns an {@link Iterator} over each entry added in this {@link FrequencyTags}
	 * @see TaggedFrequency
	 * @return an {@link Iterator}
	 */
	public Iterator<Map.Entry<Sequence, TaggedFrequency>> getEntrySetIterator();

	/**
	 * Returns the number of entry added without the occurrences.
	 * @return a long.
	 */
	public long getUniqueCount();
}