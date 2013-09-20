package bacci.giovanni.deunifier.DeUniFier.freq;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import bacci.giovanni.deunifier.DeUniFier.io.FastaPointersSequences;
import bacci.giovanni.deunifier.DeUniFier.seq.HashPointersMultiSequence;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;

public class HashFrequencyTags extends BasicFrequencyTags {

	private static final long serialVersionUID = 3869698549643522786L;

	public HashFrequencyTags(String[] tags) {
		super(tags);
	}

	@Override
	public void addSequence(Sequence seq, String tag) {
		if (!(seq instanceof HashPointersMultiSequence)) {
			throw new IllegalArgumentException(
					"This FrequencyTags can be used only for HashPointersMultiSequence");
		} else {
			Sequence s = seq;
			TaggedFrequency f = this.freqs.get(s);
			if (f == null) {
				f = new BasicTaggedFrequency();
				f.setTags(this.tags);
				f.addValue(tag);
				this.freqs.put(s, f);
				this.uniqueCount += 1L;
			} else {
				Iterator<Entry<FastaPointersSequences, List<Integer>>> it = ((HashPointersMultiSequence) seq)
						.getEntryIterator();
				HashPointersMultiSequence oldSeq = null;
				for (Sequence ss : freqs.keySet()) {
					if (ss.compareTo(seq) == 0) {
						oldSeq = (HashPointersMultiSequence) ss;
					}
				}
				while (it.hasNext()) {
					Entry<FastaPointersSequences, List<Integer>> next = it
							.next();
					FastaPointersSequences fastaPointer = next.getKey();
					for (Integer i : next.getValue()) {
						oldSeq.addIndex(fastaPointer, i);
					}
				}
				f.addValue(tag);
			}
		}
		super.addSequence(seq, tag);
	}
}
