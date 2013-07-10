package bacci.giovanni.deunifier.DeUniFier.freq;

import java.io.Serializable;

public interface TaggedFrequency extends Serializable {
	
	public long getCount(Comparable<?> c);	

	public void addValue(Comparable<?> c);
	
	public void setValue(Comparable<?> c, long value);

	public String getTabbedString(String sep, boolean header);

	public String[] getTags();

	public void setTags(String[] tags);

	public void merge(TaggedFrequency freq);
	
}