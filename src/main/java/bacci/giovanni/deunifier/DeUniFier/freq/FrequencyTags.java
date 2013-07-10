package bacci.giovanni.deunifier.DeUniFier.freq;

import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public interface FrequencyTags extends Serializable {

	public void addSequence(Sequence sequence, String tag);

	public long getCount(Sequence sequence, String tag);

	public Collection<Sequence> getSequences();

	public Collection<String> getTags();

	public Iterator<Map.Entry<Sequence, TaggedFrequency>> getEntrySetIterator();

	public long getUniqueCount();
}