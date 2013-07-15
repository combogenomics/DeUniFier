package bacci.giovanni.deunifier.DeUniFier.freq;

import java.io.Serializable;
/**
 * Table of frequency. 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public interface TaggedFrequency extends Serializable {
	
	/**
	 * Returns the occurrence of the {@link Comparable} value
	 * @param c a {@link Comparable}
	 * @return the occurrence of c
	 */
	public long getCount(Comparable<?> c);	

	/**
	 * Add a {@link Comparable} to this table of frequency.
	 * @param c a {@link Comparable}
	 */
	public void addValue(Comparable<?> c);
	
	/**
	 * Sets a specified value to a {@link Comparable} object. 
	 * @param c the {@link Comparable}
	 * @param value the value
	 */
	public void setValue(Comparable<?> c, long value);

	/**
	 * Returns a {@link String} representation of this table of frequency. Each 
	 * value will be reported using the sep parameter as separator.
	 * @param sep the separator parameter
	 * @param header specify if the header has to be reported
	 * @return a {@link String} with all values.
	 */
	public String getTabbedString(String sep, boolean header);

	/**
	 * Returns an array with all the tags added to this {@link TaggedFrequency}
	 * @return an array of {@link String}
	 */
	public String[] getTags();

	/**
	 * Set the tags that has to be reported with this {@link TaggedFrequency}
	 * @param tags an array of String with all the tags.
	 */
	public void setTags(String[] tags);

	/**
	 * Merge this frequency table with another one.
	 * @param freq another {@link TaggedFrequency}
	 */
	public void merge(TaggedFrequency freq);
	
}