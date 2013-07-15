package bacci.giovanni.deunifier.DeUniFier.assignments;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a {@link ClusterAssignment} interface. Here hits are inserted as {@link String}
 * and they are collected into a {@link List}
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 */
public class SimpleClusterAssignment implements
		ClusterAssignment<List<String>, String> {

	private List<String> hitList = null;
	
	public SimpleClusterAssignment() {
		hitList = new ArrayList<String>();
	}

	public List<String> getHits() {
		return hitList;
	}

	public void addHit(String hit) {
		hitList.add(hit);

	}

}
