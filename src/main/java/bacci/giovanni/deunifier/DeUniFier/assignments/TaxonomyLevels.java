package bacci.giovanni.deunifier.DeUniFier.assignments;

/**
 * This {@link Enum} contains all taxonomic levels that a {@link TaxAssignments} can 
 * return. 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public enum TaxonomyLevels {
	DOMAIN(1), PHYLUM(2), CLASS(3), ORDER(4), FAMILY(5), GENUS(6);
	
	public int level;
	
	/**
	 * Private constructor. An int is added to each taxonomy level in order to keep them ordered.
	 * @param level an int
	 */
	private TaxonomyLevels(int level){
		this.level = level;
	}
	
	@Override
	public String toString() {		
		return super.toString().toLowerCase();
	}
		
}
