package bacci.giovanni.deunifier.DeUniFier.itools;

import bacci.giovanni.deunifier.DeUniFier.assignments.ClustAssignments;
import bacci.giovanni.deunifier.DeUniFier.assignments.ClusterAssignment;
import bacci.giovanni.deunifier.DeUniFier.assignments.SimpleClusterAssignment;

/**
 * Implementation of a {@link ClustAssignments} object obtained with USEARCH algorithm.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class UsearchAssignments implements ClustAssignments{

	private String name = null;
	private SimpleClusterAssignment assignments = null;	

	/**
	 * Constructor.
	 * @param name the name of the read
	 * @param assignments all the hits as a {@link SimpleClusterAssignment}
	 */
	public UsearchAssignments(String name, SimpleClusterAssignment assignments) {
		this.name = name;
		this.assignments = assignments;
	}

	/**
	 * @see ClustAssignments#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see ClustAssignments#getAssignments()
	 */
	public ClusterAssignment getAssignments() {		
		return assignments;
	}

}
