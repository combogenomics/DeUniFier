package bacci.giovanni.deunifier.DeUniFier.assignments;

/**
 * A taxonmy assignment.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public interface TaxonomyAssignment {
	/**
	 * Returns the name of this assignment
	 * @return the name of the assignment as a {@link String}
	 */
	public String getName();
	
	/**
	 * Returns the accuracy of the assignments
	 * @return the accuracy of the assignments as a {@link Double}
	 */
	public double getAccuracy();
}
