package bacci.giovanni.deunifier.DeUniFier.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation of a {@link SequencePointerCollectionSet} that store a unique set of {@link Integer} (the
 * hash code generated from the {@link HashPointerSequence#getSequence()} method) associated to a {@link SequencePointerCollection}
 * that contains all the pointers to the sequences and the {@link AbstractRandomAccessFileSequences}.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class HashSequencePointerCollectionSet implements SequencePointerCollectionSet<Integer> {

	private Map<Integer, SequencePointerCollection> entryMap = null;
			
	public HashSequencePointerCollectionSet() {
		entryMap = new HashMap<Integer, SequencePointerCollection>();
	}

	public SequencePointerCollection getCollection(Integer t) {
		return entryMap.get(t);
	}

	public void addEntry(PointerSequence<Integer> sequence,
			AbstractRandomAccessFileSequences araf) {
		if(entryMap.containsKey(sequence.getSequence())){
			entryMap.get(sequence.getSequence()).addEntry(araf, sequence.getPointer());			
		}else{
			SequencePointerCollection spc = new HashSequencePointerCollection();
			spc.addEntry(araf, sequence.getPointer());
			entryMap.put(sequence.getSequence(), spc);
		}
		
	}

	public Iterator<Entry<Integer, SequencePointerCollection>> entrySetIterator() {
		return entryMap.entrySet().iterator();
	}

	public long uniquesCount() {		
		return entryMap.keySet().size();
	}

	public List<AbstractRandomAccessFileSequences> getKeys() {
		List<AbstractRandomAccessFileSequences> keyList = new ArrayList<AbstractRandomAccessFileSequences>();
		for(SequencePointerCollection spc : entryMap.values()){
			for(AbstractRandomAccessFileSequences araf : spc.keySet()){
				if(!keyList.contains(araf)){
					keyList.add(araf);
				}
			}
		}
		return keyList;
	}
	
}
