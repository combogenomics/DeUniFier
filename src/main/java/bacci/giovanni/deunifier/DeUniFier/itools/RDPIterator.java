package bacci.giovanni.deunifier.DeUniFier.itools;

import java.io.IOException;
import java.nio.file.Path;

import bacci.giovanni.deunifier.DeUniFier.assignments.TaxAssignments;

/**
 * Implementation of a {@link FileAssignmentsIterator} able to iterate over an RDP assignments file.
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class RDPIterator extends FileAssignmentsIterator<TaxAssignments> {

	/**
	 * @see FileAssignmentsIterator#FileAssignmentsIterator(Path)
	 * @param input
	 * @throws IOException
	 */
	public RDPIterator(Path input) throws IOException {
		super(input);
	}

	/**
	 * Returns a {@link TaxAssignments} object.
	 * @see FileAssignmentsIterator#next()
	 */
	public TaxAssignments next() throws IOException {
		TaxAssignments ass = new FixedRankRDPAssignments(line);
		nextLine();
		return ass;
	}

}
