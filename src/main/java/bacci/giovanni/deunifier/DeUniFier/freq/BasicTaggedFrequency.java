package bacci.giovanni.deunifier.DeUniFier.freq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math3.stat.Frequency;

/**
 * Basic implementation of a {@link TaggedFrequency}. This implementation uses a {@link Frequency} 
 * object to store {@link Comparable} values.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class BasicTaggedFrequency implements TaggedFrequency {
	private static final long serialVersionUID = 7997639733120198235L;
	protected Frequency freq = null;
	private List<String> tags = null;

	/**
	 * Constructor.
	 */
	public BasicTaggedFrequency() {
		this.freq = new Frequency();
		this.tags = new ArrayList<String>();
	}

	/**
	 * @throws IllegalArgumentException if the <code>comp</code> parameter is not present in the
	 * tags of this {@link TaggedFrequency}.
	 * 
	 * @see TaggedFrequency#getCount(Comparable)
	 * @see TaggedFrequency#getTags()
	 * @see TaggedFrequency#setTags(String[])
	 */
	public long getCount(Comparable<?> comp) {
		Comparable<?> c = comp;
		if (!this.tags.contains(c)) {
			throw new IllegalArgumentException("tag is not included");
		}
		return this.freq.getCount(comp);
	}

	/**
	 * @see TaggedFrequency#getTabbedString(String, boolean)
	 */
	public String getTabbedString(String sep, boolean header) {
		StringBuffer buffer = new StringBuffer();
		if (header) {
			for (int i = 0; i < this.tags.size(); i++) {
				buffer.append((String) this.tags.get(i) + sep);
			}
		} else {
			for (int i = 0; i < this.tags.size(); i++) {
				buffer.append(this.freq.getCount(this.tags.get(i)) + sep);
			}
		}
		return buffer.toString();
	}

	/**
	 * @see TaggedFrequency#getTags()
	 */
	public String[] getTags() {
		String[] tags = new String[this.tags.size()];
		for (int i = 0; i < tags.length; i++) {
			tags[i] = this.tags.get(i);
		}
		return tags;
	}

	/**
	 * @throws IllegalArgumentException if the <code>comp</code> parameter is not
	 * present in the tags array of this {@link TaggedFrequency}
	 * @see TaggedFrequency#addValue(Comparable)
	 * @see TaggedFrequency#setTags(String[])
	 * @see TaggedFrequency#getTags()
	 */
	public void addValue(Comparable<?> comp) {
		Comparable<?> c = comp;
		if (!this.tags.contains(c)) {
			throw new IllegalArgumentException("tag is not included");
		}
		this.freq.addValue(c);
	}

	/**
	 * @see TaggedFrequency#setTags(String[])
	 */
	public void setTags(String[] tags) {
		for (String s : tags) {
			this.tags.add(s);
			Collections.sort(this.tags);
		}
	}

	/**
	 * @throws IllegalArgumentException if the two {@link TaggedFrequency} have different 
	 * tags.
	 * @see TaggedFrequency#merge(TaggedFrequency)
	 * @see TaggedFrequency#setTags(String[])
	 * @see TaggedFrequency#getTags()
	 */
	public void merge(TaggedFrequency freq) {
		if (!Arrays.equals(freq.getTags(), getTags())) {
			throw new IllegalArgumentException("tags are different");
		}
		for (String tag : freq.getTags())
			this.freq.incrementValue(tag, freq.getCount(tag));
	}

	/**
	 * If other objects have already been added to this Frequency, <code>paramComparable</code> must be 
	 * comparable to those that have already been added.
	 * @throws IllegalArgumentException if <code>paramComparable</code> is not comparable with previous 
	 * entries
	 * @see TaggedFrequency#setValue(Comparable, long)
	 */
	public void setValue(Comparable<?> paramComparable, long value) {
		freq.incrementValue(paramComparable, value);				
	}
}