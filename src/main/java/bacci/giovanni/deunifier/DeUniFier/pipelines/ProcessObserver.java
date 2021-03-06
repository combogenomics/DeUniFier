package bacci.giovanni.deunifier.DeUniFier.pipelines;

import java.util.Observable;
import java.util.Observer;

/**
 * Implementation of an {@link Observer} interface.
 * 
 * @author <a href="http://www.unifi.it/dblage/CMpro-v-p-65.html">Giovanni
 *         Bacci</a>
 * 
 */
public class ProcessObserver implements java.util.Observer {
	double prev_prog = 0.0D;

	/**
	 * If arg1 is a {@link String} it will be printed. If arg1 is a double it
	 * will be tested against the {@link #prev_prog} field. If the differece
	 * between arg1 and {@link #prev_prog} is less that <code>0.01</code>
	 * nothing will be printed, othewrwise a percentage will be printed.
	 */
	public void update(Observable arg0, Object arg1) {
		if ((arg1 instanceof Double)) {
			double p = ((Double) arg1).doubleValue();
			if ((p - this.prev_prog > 0.01D)) {
				this.prev_prog = p;
				String res = String.format("%.2f%s done", p, "%");
				System.out.print("\r" + res);
			}
			if (p == 100.0D) {
				this.prev_prog = 0.0D;
				String res = String.format("%.2f%s done", p, "%");
				System.out.println("\r" + res);
			}
		} else {
			System.out.println(arg1);
		}
	}
}