package bacci.giovanni.deunifier.DeUniFier.assignments;

import java.util.Collection;

/**
 * Cluster assignment. This is a single assignment with all the hits clustered together.
 * The two parameters E and T refers to all the hits together and a single hits type respectively.<br>
 * For Example:<br>
 * <p><code>
 * ClusterAssignment<List<String>, String> ass = ...<br>
 * </p></code>
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni Bacci</a>
 *
 * @param <E> all the hits collection 
 * @param <T> a single hits 
 */
public interface ClusterAssignment<E extends Collection<T>, T> {
	/**
	 * Return all the hits as a {@link Collection}
	 * @return all the hits clustered together
	 */
	public E getHits();
	
	/**
	 * Ad an hit to the {@link Collection}
	 * @param hit the hit to add
	 */
	public void addHit(T hit);
}
