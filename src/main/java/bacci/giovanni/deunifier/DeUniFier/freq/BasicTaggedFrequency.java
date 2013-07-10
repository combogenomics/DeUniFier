package bacci.giovanni.deunifier.DeUniFier.freq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math3.stat.Frequency;

public class BasicTaggedFrequency implements TaggedFrequency {
	private static final long serialVersionUID = 7997639733120198235L;
	protected Frequency freq = null;
	private List<String> tags = null;

	public BasicTaggedFrequency() {
		this.freq = new Frequency();
		this.tags = new ArrayList<String>();
	}

	public long getCount(Comparable<?> comp) {
		Comparable<?> c = comp;
		if (!this.tags.contains(c)) {
			throw new IllegalArgumentException("tag is not included");
		}
		return this.freq.getCount(comp);
	}

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

	public String[] getTags() {
		String[] tags = new String[this.tags.size()];
		for (int i = 0; i < tags.length; i++) {
			tags[i] = this.tags.get(i);
		}
		return tags;
	}

	public void addValue(Comparable<?> comp) {
		Comparable<?> c = comp;
		if (!this.tags.contains(c)) {
			throw new IllegalArgumentException("tag is not included");
		}
		this.freq.addValue(c);
	}

	public void setTags(String[] tags) {
		for (String s : tags) {
			this.tags.add(s);
			Collections.sort(this.tags);
		}
	}

	public void merge(TaggedFrequency freq) {
		if (!Arrays.equals(freq.getTags(), getTags())) {
			throw new IllegalArgumentException("tags are different");
		}
		for (String tag : freq.getTags())
			this.freq.incrementValue(tag, freq.getCount(tag));
	}

	public void setValue(Comparable<?> paramComparable, long value) {
		freq.incrementValue(paramComparable, value);				
	}
}