package bacci.giovanni.deunifier.DeUniFier.assignments;

/**
 * A Cluster Assignments.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public interface ClustAssignments extends Assignments {

	/**
	 * Return a {@link ClusterAssignment}
	 * @return an assignemnt
	 */
	public ClusterAssignment<?, ?> getAssignments();

}
