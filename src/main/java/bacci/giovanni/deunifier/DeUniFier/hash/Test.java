package bacci.giovanni.deunifier.DeUniFier.hash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Test {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Path p = Paths.get("/home/giovanni/Progetti/16s_Databases/raw/rdp_release_10_bact_only_2488213seqs_with_unclassifed_organisms.fa");
		HashSequencePointerCollectionSet collection = new HashSequencePointerCollectionSet();
		RandomAccessFile raf = new RandomAccessFile(p.toFile(), "r");
		FastaHashRandomAccessFile fastaFile = new FastaHashRandomAccessFile(
				raf, p.getFileName().toString());
		PointerSequence<Integer> seq = null;
		long begin = System.currentTimeMillis();
		long numSeq = 0;
		while ((seq = fastaFile.nextSequence()) != null) {
			numSeq++;
			collection.addEntry(seq, fastaFile);
		}
		Iterator<Entry<Integer, SequencePointerCollection>> it = collection
				.entrySetIterator();
		while (it.hasNext()) {
			Entry<Integer, SequencePointerCollection> next = it.next();
			Iterator<Entry<AbstractRandomAccessFileSequences, List<Long>>> subIt = next
					.getValue().entrySetIterator();
			while (subIt.hasNext()) {
				Entry<AbstractRandomAccessFileSequences, List<Long>> subNext = subIt
						.next();
				for (long l : subNext.getValue()) {
					subNext.getKey().getStringSequence(l).getId();
				}
			}
			
		}
		System.out.println("Analyzed " + numSeq + " sequences in " + (System.currentTimeMillis() - begin)/1000 + " seconds.");		
	}

}
