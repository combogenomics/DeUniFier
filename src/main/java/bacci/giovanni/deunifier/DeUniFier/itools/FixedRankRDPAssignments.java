package bacci.giovanni.deunifier.DeUniFier.itools;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import bacci.giovanni.deunifier.DeUniFier.assignments.SimpleTaxonomyAssignment;
import bacci.giovanni.deunifier.DeUniFier.assignments.TaxAssignments;
import bacci.giovanni.deunifier.DeUniFier.assignments.TaxonomyAssignment;
import bacci.giovanni.deunifier.DeUniFier.assignments.TaxonomyLevels;

/**
 * Implementation of a {@link TaxAssignments} based on an assignments file created with the RDP classifier or 
 * multiclassfier. 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class FixedRankRDPAssignments implements TaxAssignments {

	private Map<TaxonomyLevels, TaxonomyAssignment> map = null;
	private String name = null;
		
	/**
	 * Constructor. Given an rdp assignment as a {@link String} this constructor will parse 
	 * the assignment.
	 * @param rdpAssignment
	 */
	public FixedRankRDPAssignments(String rdpAssignment) {
		this.map = new HashMap<TaxonomyLevels, TaxonomyAssignment>();
		String[] ass = rdpAssignment.split("\t");
		this.name = ass[0];
		for (int i = 2; i < ass.length; i++) {
			String s = cleanString(ass[i]);
			for (TaxonomyLevels t : TaxonomyLevels.values()) {
				if (s.equals(t.toString())) {
					String taxName = cleanString(ass[i - 1]);
					double accuracy = Double.valueOf(ass[i + 1]);
					map.put(t, new SimpleTaxonomyAssignment(taxName, accuracy));
				}
			}
		}
	}

	/**
	 * The name of the read that produced the assignment.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see TaxAssignments#getAssignment(TaxonomyLevels)
	 */
	public TaxonomyAssignment getAssignment(TaxonomyLevels level) {
		if (map.get(level) == null) {
			return new SimpleTaxonomyAssignment("NA", 0.0D);
		}
		return map.get(level);
	}

	/**
	 * @see TaxAssignments#getLineage(boolean)
	 */
	public String getLineage(boolean accuracy) {
		Arrays.sort(TaxonomyLevels.values(), new TaxonomyLevelscomparator());
		StringBuffer lineage = new StringBuffer();
		for (int i = 0; i < TaxonomyLevels.values().length; i++) {
			TaxonomyAssignment assignment = getAssignment(TaxonomyLevels.values()[i]);
			lineage.append(assignment.getName());
			if (accuracy) {
				lineage.append("("
						+ assignment.getAccuracy()
						+ ")");
			}
			if (i != TaxonomyLevels.values().length - 1) {
				lineage.append(";");
			}
		}
		return lineage.toString();
	}

	/**
	 * Utility method able to replace all special character from the RDP assignment string
	 * @param string the assignemnt string
	 * @return a cleaned {@link String}
	 */
	private String cleanString(String string) {
		String cleaned = string.trim().replaceAll("[^a-zA-Z0-9]", "");
		return cleaned;
	}

	/**
	 * A comparator used to order {@link TaxonomyLevels} based on their rank.
	 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
	 *
	 */
	class TaxonomyLevelscomparator implements Comparator<TaxonomyLevels> {

		public int compare(TaxonomyLevels o1, TaxonomyLevels o2) {
			return o1.compareTo(o2);
		}

	}

}
