package bacci.giovanni.deunifier.DeUniFier.freq;

import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Basic implementation of a {@link FrequencyTags}.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class BasicFrequencyTag implements FrequencyTags {
	private static final long serialVersionUID = 7805418309814892623L;
	private Map<Sequence, TaggedFrequency> freqs = null;
	private String[] tags = null;
	private long uniqueCount = 0L;

	/**
	 * Constructor specifying the tags to be used for each sequence
	 * @param tags the tags to be used with each sequence
	 */
	public BasicFrequencyTag(String[] tags) {
		this.freqs = new TreeMap<Sequence, TaggedFrequency>();
		this.tags = tags;
	}

	/**
	 * @see FrequencyTags#addSequence(Sequence, String)
	 * @see TaggedFrequency#addValue(Comparable)
	 */
	public void addSequence(Sequence seq, String tag) {
		Sequence s = seq;
		TaggedFrequency f = this.freqs.get(s);
		if (f == null) {
			f = new BasicTaggedFrequency();
			f.setTags(this.tags);
			f.addValue(tag);
			this.freqs.put(s, f);
			this.uniqueCount += 1L;
		} else {
			f.addValue(tag);
		}
	}

	/**
	 * @see FrequencyTags#getCount(Sequence, String)
	 */
	public long getCount(Sequence seq, String tag) {
		Sequence s = seq;
		TaggedFrequency f = this.freqs.get(s);
		if(f == null){
			return 0;
		}
		return f.getCount(tag);
	}

	/**
	 * @see FrequencyTags#getSequences()
	 */
	public Collection<Sequence> getSequences() {
		return this.freqs.keySet();
	}

	/**
	 * @see FrequencyTags#getTags()
	 */
	public Collection<String> getTags() {
		Collection<String> tags = new ArrayList<String>();
		for (String s : this.tags) {
			tags.add(s);
		}
		return tags;
	}

	/**
	 * @see FrequencyTags#getEntrySetIterator()
	 */
	public Iterator<Entry<Sequence, TaggedFrequency>> getEntrySetIterator() {
		return this.freqs.entrySet().iterator();
	}

	/**
	 * @see FrequencyTags#getUniqueCount()
	 */
	public long getUniqueCount() {
		return this.uniqueCount;
	}
}