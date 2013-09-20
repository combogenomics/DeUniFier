package bacci.giovanni.deunifier.DeUniFier.seq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bacci.giovanni.deunifier.DeUniFier.io.FastaPointersSequences;

/**
 * Sequence class that convert each sequence into an hash code using {@link String#hashCode()} methods.
 * This class has to be used in couple with {@link FastaPointersSequences} class and each sequences index
 * has to be stored using the {@link #addIndex(FastaPointersSequences, int)} method
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class HashPointersMultiSequence implements Sequence {
	
	private static final long serialVersionUID = 3809671749371497998L;
	private Integer seqHash;		
	private Map<FastaPointersSequences, List<Integer>> indexes = null;
	
	/**
	 * Constructor.
	 * @param seq sequence 
	 * @param fps the {@link FastaPointersSequences} 
	 * @param index index of the sequence
	 */
	public HashPointersMultiSequence(String seq, FastaPointersSequences fps, int index){
		this.seqHash = seq.hashCode();
		this.indexes = new LinkedHashMap<FastaPointersSequences, List<Integer>>();
		List<Integer> indexes = new ArrayList<Integer>();
		indexes.add(index);
		this.indexes.put(fps, indexes);
	}	
	
	/**
	 * Add an index of another sequence in the {@link FastaPointersSequences}. You can add same sequences but also 
	 * different sequences.
	 * @param fps the {@link FastaPointersSequences}
	 * @param index the index
	 */
	public void addIndex(FastaPointersSequences fps, int index){
		if(indexes.get(fps) == null){
			List<Integer> indexesList = new ArrayList<Integer>();
			indexesList.add(index);			
			indexes.put(fps, indexesList);
		}else{
			indexes.get(fps).add(index);
		}
	}
	
	/**
	 * @see Comparable#compareTo(Object) mothod
	 * @throws IllegalArgumentException if you are trying to compare a sequence that is not an instance of 
	 * {@link HashPointersMultiSequence}
	 */
	public int compareTo(Sequence arg0) {
		if(arg0 instanceof HashPointersMultiSequence){
			return seqHash.compareTo(((HashPointersMultiSequence) arg0).getHash());
		}else{
			throw new IllegalArgumentException("HashSequences cannot be compared with other sequences");
		}
	}
	
	/**
	 * Get the hash of the sequence
	 * @return the hash code of the sequence
	 */
	public Integer getHash(){
		return seqHash;
	}

	/**
	 * This method scans into the first {@link FastaPointersSequences} and took the first sequence in it.
	 * If there are no Sequences this method returns <code>null</code>
	 */
	public String getSequence() {
		Iterator<Entry<FastaPointersSequences, List<Integer>>> it = indexes.entrySet().iterator();
		Entry<FastaPointersSequences, List<Integer>> next = it.next();
		Sequence s = null;
		if(next != null){
			try {
				s = next.getKey().get(next.getValue().get(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return s.getSequence();
	}

	/**
	 * Same as {@link HashPointersMultiSequence#getSequence()} but with id
	 */
	public String getId() {
		Iterator<Entry<FastaPointersSequences, List<Integer>>> it = indexes.entrySet().iterator();
		Entry<FastaPointersSequences, List<Integer>> next = it.next();
		Sequence s = null;
		if(next != null){
			try {
				s = next.getKey().get(next.getValue().get(0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return s.getId();
	}
	
	/**
	 * This method returns all the ids of each index added to this object as a {@link List} of {@link String}
	 * @return all the ids as a List of String or null if there are no sequences
	 * @throws IOException if there is an error reading in the files
	 */
	public List<String> getIdsAsStrings() throws IOException{
		List<String> ids = new ArrayList<String>();
		Iterator<Entry<FastaPointersSequences, List<Integer>>> it = indexes.entrySet().iterator();
		while(it.hasNext()){
			Entry<FastaPointersSequences, List<Integer>> next = it.next();
			for(int i : next.getValue()){
				ids.add(next.getKey().get(i).getId());
			}
		}
		return ids;
	}
	
	public Iterator<Entry<FastaPointersSequences, List<Integer>>> getEntryIterator(){
		return indexes.entrySet().iterator();
	}

	public Map<Attributes, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}


}
