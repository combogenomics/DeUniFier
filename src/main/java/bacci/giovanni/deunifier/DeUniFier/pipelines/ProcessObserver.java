package bacci.giovanni.deunifier.DeUniFier.pipelines;

import java.util.Observable;

public class ProcessObserver implements java.util.Observer {
	double prev_prog = 0.0D;

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