package bacci.giovanni.deunifier.DeUniFier.assignments;

/**
 * Taxonomy assignments.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public interface TaxAssignments extends Assignments {

	/**
	 * Returns a {@link TaxonomyAssignment} at a specified {@link TaxonomyLevels}
	 * @param level a specified taxonomy level
	 * @return an Assignment
	 */
	public TaxonomyAssignment getAssignment(TaxonomyLevels level);

	/**
	 * Returns a {@link String} with all taxonomy levels.
	 * @param accuracy if this is set to <code>true</code> after each taxonomy level will be displayed the accuracy
	 * @return a {@link String} representing the lineage
	 */
	public String getLineage(boolean accuracy);

}
