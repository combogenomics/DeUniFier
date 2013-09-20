package bacci.giovanni.deunifier.DeUniFier.freq;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bacci.giovanni.deunifier.DeUniFier.io.FastaPointersSequences;
import bacci.giovanni.deunifier.DeUniFier.io.FastaSequenceReader;
import bacci.giovanni.deunifier.DeUniFier.io.FastaSequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SimpleFrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.seq.HashPointersMultiSequence;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;

public class BTW {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// // Burrows–Wheeler transform
		// Comparator<byte[]> arrayComp = new Comparator<byte[]>() {
		// public int compare(byte[] o1, byte[] o2) {
		// return Byte.compare(o1[0], o2[0]);
		// }
		// };
		// char start = '?';
		// String a = (start + args[0]).toLowerCase();
		// byte[][] matrix = new byte[a.length()][a.length()];
		// matrix[0] = a.getBytes();
		// for (int i = 1; i < a.length(); i++) {
		// System.arraycopy(matrix[i - 1], 0, matrix[i], 1,
		// matrix[i - 1].length - 1);
		// matrix[i][0] = matrix[i - 1][matrix[i - 1].length - 1];
		// }
		// Arrays.sort(matrix, arrayComp);
		// byte[] res = new byte[a.length()];
		// for (int i = 0; i < a.length(); i++) {
		// res[i] = matrix[i][a.length() - 1];
		// }
		//
		// matrix = new byte[res.length][res.length];
		// a = new String(res);
		// System.out.println(a);
		// // FINE
		//
		// // Burrows–Wheeler decoding

		// // Linguaggio a triplette
		// char[] dna = { 'a', 'c', 'g', 't' };
		// Map<String, Byte> encode = new LinkedHashMap<String, Byte>();
		// Map<Byte, String> decode = new LinkedHashMap<Byte, String>();
		// byte mapped = 0;
		//
		// System.out.println(args[0].toLowerCase());
		// System.out.println(args[0].length());
		//
		// for (char one : dna) {
		// for (char two : dna) {
		// for (char three : dna) {
		// char[] combination = { one, two, three };
		// encode.put(String.valueOf(combination), mapped);
		// decode.put(mapped, String.valueOf(combination));
		// mapped++;
		// }
		// }
		// }
		//
		// for (char one : dna) {
		// char[] combination = { one, ' ', ' ' };
		// encode.put(String.valueOf(combination), mapped);
		// decode.put(mapped, String.valueOf(combination));
		// mapped++;
		// }
		//
		// for (char one : dna) {
		// for (char two : dna) {
		// char[] combination = { one, two, ' ' };
		// encode.put(String.valueOf(combination), mapped);
		// decode.put(mapped, String.valueOf(combination));
		// mapped++;
		// }
		// }
		//
		// while((args[0].length() % 3) > 0){
		// args[0] = args[0] + ' ';
		// }
		//
		// int begin = 0;
		// int end = 3;
		// byte[] seq = new byte[args[0].length()/3];
		//
		// for(int i = 0; i < seq.length; i++){
		// seq[i] = encode.get(args[0].substring(begin, end).toLowerCase());
		// begin += 3;
		// end += 3;
		// }
		//
		// System.out.println(Arrays.toString(seq));
		// System.out.println(seq.length);
		//
		// StringBuffer buffer = new StringBuffer();
		// for(int i = 0; i < seq.length; i++){
		// buffer.append(decode.get(seq[i]));
		// }
		//
		// System.out.println(buffer.toString().trim());
		// System.out.println(buffer.toString().trim().length());
		// // FINE

		// RandomAccessFile raf = new RandomAccessFile(
		// new File(
		// "/home/giovanni/Progetti/16s_Databases/raw/test.fasta"),
		// "r");
		//
		// String line = null;
		// List<Long> pointers = new ArrayList<Long>();
		// long points = raf.getFilePointer();
		// boolean done = false;
		// while((line = raf.readLine()) != null){
		// if(line.startsWith(">")){
		// pointers.add(points);
		// }else{
		// points = raf.getFilePointer();
		// }
		// }
		// pointers.add(raf.getFilePointer());
		//
		// System.out.println(pointers.size());
		// int last = pointers.size() - 1;
		// // raf.close();
		// // raf = new RandomAccessFile(
		// // new File(
		// //
		// "/home/giovanni/Progetti/16s_Databases/raw/rdp_release_10_bact_only_2488213seqs_with_unclassifed_organisms.fa"),
		// // "r");
		// long begin = System.nanoTime();
		// raf.seek(pointers.get(last - 1));
		// while(raf.getFilePointer() < pointers.get(last)){
		// System.out.println(raf.readLine());
		// }
		//
		// System.out.println(System.nanoTime() - begin);
		//
		// begin = System.nanoTime();
		// raf.seek(pointers.get(0));
		// while(raf.getFilePointer() < pointers.get(1)){
		// System.out.println(raf.readLine());
		// }
		//
		// System.out.println(System.nanoTime() - begin);
		//
		// raf.close();

		Path p = Paths.get("/home/giovanni/Progetti/16s_Databases/raw/");
		DirectoryStream<Path> stream = Files.newDirectoryStream(p);
		List<String> names = new ArrayList<String>();
		for (Path in : stream) {
			names.add(in.getFileName().toString());
		}
		HashFrequencyTags hft = new HashFrequencyTags(
				names.toArray(new String[names.size()]));
		FastaPointersSequences fps = null;
		HashPointersMultiSequence hpms = null;
		stream = Files.newDirectoryStream(p);
		for (Path in : stream) {
			long begin = System.currentTimeMillis();
			System.out.println("Analyze " + in.toString() + " ...");
			fps = new FastaPointersSequences(in);
			System.out.println("Done in: " + (System.currentTimeMillis() - begin)/1000 + " sec.");
			System.out.println("File has: " + fps.size() + " sequences. Begin redundancy control ...");
			for (int i = 1; i < fps.size(); i++) {
				hpms = new HashPointersMultiSequence(fps.get(i).getSequence(),
						fps, i);
				hft.addSequence(hpms, in.getFileName().toString());
			}
			System.out.println("Done in: " + (System.currentTimeMillis() - begin)/1000 + " sec.");
		}

		System.out.println("Writing output ...");
		Iterator<Entry<Sequence, TaggedFrequency>> it = hft
				.getEntrySetIterator();
		Path out = Paths.get("/home/giovanni/Progetti/16s_Databases/clean/");
		BufferedWriter bw = Files.newBufferedWriter(out.resolve("ids.txt"),
				Charset.defaultCharset());
		FastaSequenceWriter fsw = new FastaSequenceWriter(
				Files.newBufferedWriter(out.resolve("seq.fasta"),
						Charset.defaultCharset()));
		SimpleFrequencyWriter sfw = new SimpleFrequencyWriter(";",
				Files.newBufferedWriter(out.resolve("freq.csv"),
						Charset.defaultCharset()));
		boolean header = true;
		while (it.hasNext()) {
			Entry<Sequence, TaggedFrequency> next = it.next();
			boolean first = true;
			for (String id : ((HashPointersMultiSequence) next.getKey())
					.getIdsAsStrings()) {
				if (first) {
					bw.write(id);
					first = false;
				} else {
					bw.write("     " + id);
				}
				bw.newLine();
				bw.flush();
			}
			fsw.writeSequence(next.getKey());
			sfw.write(next, header);
			header = false;
		}
		System.out.println("Done.");
	}

}
