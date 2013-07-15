package bacci.giovanni.deunifier.DeUniFier.assignments;

public class SimpleTaxonomyAssignment implements TaxonomyAssignment {

	private String name = null;
	private double accuracy = 0.0;

	public SimpleTaxonomyAssignment(String name, double accuracy) {
		this.name = name;
		this.accuracy = accuracy;
	}

	public String getName() {
		return name;
	}

	public double getAccuracy() {
		return accuracy;
	}

}
