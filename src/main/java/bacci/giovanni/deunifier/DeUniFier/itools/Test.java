package bacci.giovanni.deunifier.DeUniFier.itools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observer;

import org.apache.commons.math3.stat.Frequency;

import bacci.giovanni.deunifier.DeUniFier.assignments.ClustAssignments;
import bacci.giovanni.deunifier.DeUniFier.assignments.TaxAssignments;
import bacci.giovanni.deunifier.DeUniFier.assignments.TaxonomyLevels;
import bacci.giovanni.deunifier.DeUniFier.freq.BasicTaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.freq.TaggedFrequency;
import bacci.giovanni.deunifier.DeUniFier.io.FastaSequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.io.FrequencyReader;
import bacci.giovanni.deunifier.DeUniFier.io.FrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SequenceWriter;
import bacci.giovanni.deunifier.DeUniFier.io.SimpleFrequencyReader;
import bacci.giovanni.deunifier.DeUniFier.io.SimpleFrequencyWriter;
import bacci.giovanni.deunifier.DeUniFier.pipelines.ProcessObserver;
import bacci.giovanni.deunifier.DeUniFier.pipelines.unifier.FileCounter;
import bacci.giovanni.deunifier.DeUniFier.pipelines.unifier.TagFileCounterMod;
import bacci.giovanni.deunifier.DeUniFier.seq.Sequence;

public class Test {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Path p_clust = Paths
				.get("/home/giovanni/Progetti/Bolzano/Cina_Extremophiles/usearch_output/extremophiles_clusters.uc");
		Path p_rdp = Paths
				.get("/home/giovanni/Progetti/Bolzano/Cina_Extremophiles/rdp_output/extremophiles_rdp.txt");
		Path p_freq = Paths
				.get("/home/giovanni/Progetti/Bolzano/Cina_Extremophiles/deunifier_output/tab.csv");

		// Path in = Paths
		// .get("/home/giovanni/Progetti/Bolzano/Cina_Extremophiles/DATA/");
		// Path out = Paths.get("/home/giovanni/Desktop/");
		// DirectoryStream<Path> stream = Files.newDirectoryStream(in);
		// List<Path> pathList = new ArrayList<Path>();
		//
		// for (Path p : stream) {
		// pathList.add(p);
		// }
		//
		// FileCounter c = new TagFileCounterMod(pathList, 3);
		// Observer o = new ProcessObserver();
		//
		// c.addObserver(o);
		//
		// SequenceWriter sw = new FastaSequenceWriter(Files.newBufferedWriter(
		// out.resolve("seq.fasta"), Charset.defaultCharset()));
		// FrequencyWriter fw = new SimpleFrequencyWriter(";",
		// Files.newBufferedWriter(out.resolve("tab.csv"),
		// Charset.defaultCharset()));
		// BufferedWriter bw = Files.newBufferedWriter(out.resolve("ids.txt"),
		// Charset.defaultCharset());
		//
		// c.writeOutput(sw, fw, bw);

		FileAssignmentsIterator<TaxAssignments> it_rdp = new RDPIterator(p_rdp);
		// FileAssignmentsIterator<ClustAssignments> it_clust = new
		// UsearchIterator(p_clust);
		// BufferedReader br = Files.newBufferedReader(p_freq,
		// Charset.defaultCharset());
		// FrequencyReader fr = new SimpleFrequencyReader(";", br, true);

		Entry<Sequence, TaggedFrequency> entry = null;
		Path out = Paths
				.get("/home/giovanni/Progetti/Bolzano/Cina_Extremophiles/deunifier_output/assignments_reconstruct.csv");

		BufferedWriter mainWriter = Files.newBufferedWriter(out,
				Charset.defaultCharset());
		boolean header = true;
		long otu = 1L;

		while (it_rdp.hasNext()) {
			StringBuffer buffer = new StringBuffer();
			TaxAssignments rdp_entry = it_rdp.next();
			FileAssignmentsIterator<ClustAssignments> it_clust = new UsearchIterator(
					p_clust);
			while (it_clust.hasNext()) {
				ClustAssignments clust_entry = it_clust.next();
				if (rdp_entry.getName().equals(clust_entry.getName())) {
					List<String> hits = (List<String>) clust_entry
							.getAssignments().getHits();
					TaggedFrequency frequency = new BasicTaggedFrequency();
					int found = 0;
					BufferedReader br = Files.newBufferedReader(p_freq,
							Charset.defaultCharset());
					FrequencyReader fr = new SimpleFrequencyReader(";", br,
							true);
					Entry<Sequence, TaggedFrequency> freq = null;
					int length = hits.size();

					while ((freq = fr.readNextFrequency()) != null) {
						String id = freq.getKey().getId();						
						if (length > 0) {
							for (String s : hits) {
								if (id.contains(s)) {
									if (frequency.getTags().length == 0) {
										frequency.setTags(freq.getValue()
												.getTags());
									}
									length--;
									frequency.merge(freq.getValue());
								}
							}
						}else{
							break;
						}
					}

					// for (String s : hits) {
					// BufferedReader br = Files.newBufferedReader(p_freq,
					// Charset.defaultCharset());
					// FrequencyReader fr = new SimpleFrequencyReader(";", br,
					// true);
					// Entry<Sequence, TaggedFrequency> freq = null;
					// while ((freq = fr.readNextFrequency()) != null) {
					// String id = freq.getKey().getId();
					// if (id.contains(s)) {
					// if (frequency.getTags().length == 0) {
					// frequency
					// .setTags(freq.getValue().getTags());
					// }
					// frequency.merge(freq.getValue());
					// found++;
					// System.out.println("Found " + found);
					// break;
					// }
					// }
					// }

					if (header) {
						buffer.append("otu;domain;phylum;class;order;family;genus;"
								+ frequency.getTabbedString(";", true)
								+ System.lineSeparator());
						header = false;
					}
					buffer.append(otu + ";" + rdp_entry.getLineage(true) + ";");
					buffer.append(frequency.getTabbedString(";", false));
					otu++;
					mainWriter.write(buffer.toString());
					mainWriter.newLine();
					mainWriter.flush();
					break;
				}
			}
		}
		mainWriter.close();
		// while(it_clust.hasNext()){
		// ClustAssignments entry = it_clust.next();
		// System.out.println(entry.getName());
		// System.out.println(((List<String>)entry.getAssignments().getHits()).size());
		// }

	}

}
