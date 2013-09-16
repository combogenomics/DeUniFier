package bacci.giovanni.deunifier.DeUniFier.itools;

import java.io.IOException;
import java.nio.file.Path;

import bacci.giovanni.deunifier.DeUniFier.assignments.ClustAssignments;
import bacci.giovanni.deunifier.DeUniFier.assignments.SimpleClusterAssignment;

/**
 * * Implementation of a {@link FileAssignmentsIterator} able to iterate over
 * the hits of a USEARCH output file (the file with <code>.uc</code> extension)
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 * 
 */
public class UsearchIterator extends FileAssignmentsIterator<ClustAssignments> {

	private static final String USEARCH_SEPARATOR = "\t";
	private static final String USEARCH_SEED = "S";
	private static final String USEARCH_CLUSTER = "C";
	private static final String USEARCH_HIT = "H";

	private static final int CLUSTER_INDEX = 1;
	private static final int QUERY_INDEX = 8;

	private long clustersNumber = -1L;
	private long clusterIterated = 0L;

	/**
	 * @see FileAssignmentsIterator#FileAssignmentsIterator(Path)
	 * @param input
	 * @throws IOException
	 */
	public UsearchIterator(Path input) throws IOException {
		super(input);
		while (super.hasNext()) {
			if (isCluster(line)) {
				clustersNumber++;
			}
			nextLine();
		}
		reset();
	}

	/**
	 * @see FileAssignmentsIterator#next()
	 */
	@Override
	public ClustAssignments next() throws IOException {
		SimpleClusterAssignment assignments = new SimpleClusterAssignment();
		String name = null;
		while (super.hasNext()) {
			if (getclusterNumber(line) == clusterIterated) {
				if (isSeed(line)) {
					if (name != null) {
						throw new IllegalArgumentException(
								"Two seed for the same cluster!");
					}
					name = getQuery(line);
					assignments.addHit(name);
				} else if (isHit(line)) {
					assignments.addHit(getQuery(line));
				}
			}
			nextLine();
		}
		reset();
		clusterIterated++;
		return new UsearchAssignments(name, assignments);
	}

	/**
	 * @see FileAssignmentsIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return clusterIterated < clustersNumber;
	}

	/**
	 * Utility method able to return the cluster number given a usearch hit
	 * as a String
	 * 
	 * @param line
	 *            usearch hit
	 * @return the cluster number of this hit as an integer
	 */
	private int getclusterNumber(String line) {
		int res = Integer.valueOf(line.split(USEARCH_SEPARATOR)[CLUSTER_INDEX]);
		return res;
	}

	/**
	 * Utility method that able to return the query name given a usearch hit as
	 * a String
	 * 
	 * @param line
	 *            usearch hit
	 * @return the query name given a usearch hit as a String
	 */
	private String getQuery(String line) {
		return line.split(USEARCH_SEPARATOR)[QUERY_INDEX];
	}

	/**
	 * Utility method that return <code>true</code> if this usearch hit contain
	 * a seed hit, otherwise this method will return <code>false</code>
	 * 
	 * @param line
	 *            usearch hit
	 * @return <code>true</code> if this hit is a seed
	 */
	private boolean isSeed(String line) {
		return line.startsWith(USEARCH_SEED);
	}

	/**
	 * Utility method that return <code>true</code> if this usearch hit contain
	 * a cluster hit, otherwise this method will return <code>false</code>
	 * 
	 * @param line
	 *            usearch hit
	 * @return <code>true</code> if this hit is a cluster
	 */
	private boolean isCluster(String line) {
		return line.startsWith(USEARCH_CLUSTER);
	}

	/**
	 * Utility method that return <code>true</code> if this usearch hit contain
	 * a hit hit, otherwise this method will return <code>false</code>
	 * 
	 * @param line
	 *            usearch hit
	 * @return <code>true</code> if this hit is a hit
	 */
	private boolean isHit(String line) {
		return line.startsWith(USEARCH_HIT);
	}

}
