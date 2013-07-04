package bacci.giovanni.deunifier.DeUniFier.freq;

import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class BasicFrequencyTag implements FrequencyTags {
	private static final long serialVersionUID = 7805418309814892623L;
	private Map<Sequence, TaggedFrequency> freqs = null;
	private String[] tags = null;
	private long uniqueCount = 0L;

	public BasicFrequencyTag(String[] tags) {
		this.freqs = new TreeMap<Sequence, TaggedFrequency>();
		this.tags = tags;
	}

	public void addSequence(Sequence seq, String tag) {
		Sequence s = seq;
		TaggedFrequency f = (TaggedFrequency) this.freqs.get(s);
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

	public long getCount(Sequence seq, String tag) {
		Sequence s = seq;
		TaggedFrequency f = (TaggedFrequency) this.freqs.get(s);
		return f.getCount(tag);
	}

	public Collection<Sequence> getSequences() {
		return this.freqs.keySet();
	}

	public Collection<String> getTags() {
		Collection<String> tags = new ArrayList<String>();
		for (String s : this.tags) {
			tags.add(s);
		}
		return tags;
	}

	public Iterator<Entry<Sequence, TaggedFrequency>> getEntrySetIterator() {
		return this.freqs.entrySet().iterator();
	}

	public long getUniqueCount() {
		return this.uniqueCount;
	}
}