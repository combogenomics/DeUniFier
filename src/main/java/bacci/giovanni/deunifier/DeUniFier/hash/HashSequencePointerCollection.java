package bacci.giovanni.deunifier.DeUniFier.hash;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

public class HashSequencePointerCollection implements
		SequencePointerCollection {
	
	private Map<AbstractRandomAccessFileSequences, List<Long>> entryMap = null;

	public HashSequencePointerCollection() {
		this.entryMap = new HashMap<AbstractRandomAccessFileSequences, List<Long>>();
	}

	public Iterator<Entry<AbstractRandomAccessFileSequences, List<Long>>> entrySetIterator() {
		return entryMap.entrySet().iterator();
	}

	public void addEntry(AbstractRandomAccessFileSequences araf, long pointer) {
		List<Long> list = entryMap.get(araf);
		if(list != null){
			list.add(pointer);
		}else{
			list = new Vector<Long>();
			list.add(pointer);
			entryMap.put(araf, list);
		}
		
	}
	
	public Set<AbstractRandomAccessFileSequences> keySet(){
		return entryMap.keySet();
	}

}
