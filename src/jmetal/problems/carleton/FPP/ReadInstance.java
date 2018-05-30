package jmetal.problems.carleton.FPP;

import java.awt.Font;

import carleton.Instance;
import edu.princeton.cs.introcs.Draw;

public class ReadInstance {
	private String fileName_; // name of the file containing the instance
	private int[][] a_matrix; // a matrix describing the problem
	private int[][][] b_matrixs; // k b matrixs describing the problem
	public int fogs = -1; // number of facilities
	public int objectives_ = 2; // number of objectives
	public int clients = -1;
	double[][] d_matrix;
	int[] cpu_demand;
	int[] mem_demand;
	int[] bandwidth_demand;
	Draw d = null;
	Instance r = null;

	public ReadInstance(String name) {
		r = new Instance(30, 10, name);
		fogs = r.fogs;
		clients = r.clents;
		d_matrix = new double[clients][fogs + 1];
		mem_demand = r.mem;
		cpu_demand = r.cpu;
		bandwidth_demand = r.bandwidth;

		for (int i = 0; i < clients; i++) {
			for (int j = 0; j < fogs; j++) {
				double dist = r.distClientToFog(i, j);
				double b = (double) r.packets[i] * 12000 / ((double) r.wifiType[i] * 1000000) + dist / 177000
						+ 0.0000125;
				assert b > 0.0 : "b is hahha";
				d_matrix[i][j] = Math.round(b * 1000 + 0.5d);
			}
			double a = (double) r.bandwidth[i] * 8;
			// System.out.println("bandwith in bits "+ (a));
			double t = a / ((double) r.wifiType[i] * 1000000) + 0.0056 + 0.000075;
			assert Double.compare(t, 0.0) > 0 : "hahaha " + a;
			d_matrix[i][fogs] = Math.round(t * 1000 + 0.5d);
		}

	}

	void draw() {
		d = new Draw("FPP_Network");
		d.setXscale(0, 100);
		d.setYscale(0, 100);
		d.setPenColor(Draw.BLUE);
		d.setPenRadius(0.0025);
		d.clear();
		for (int i = 0; i < clients; i++) {
			// double []po = new d{r.rx,r.ry};
			d.filledCircle(r.rx[i], r.ry[i], 0.5);
			d.setFont(new Font("", Font.BOLD, 10));
			// System.out.println("trying"+( r.rx[i]));
			d.textLeft(r.rx[i] + 1, r.ry[i], "E" + (i + 1));
		}
		for (int i = 0; i < fogs; i++) {
			double[] sites = r.Nodes[i];
			;
			d.square(sites[0], sites[1], 1.0);
			d.setFont(new Font("", Font.BOLD, 15));
			d.textLeft(sites[0] + 1, sites[1] + 1, "P" + (i + 1));
			// d.line(clpo[0], clpo[1], po[0] , po[1]);
		}
		int[] crouting = new int[] { 4, 9, 10, 3, 10, 5, 4, 4, 5, 5, 3, 4, 5, 9, 5, 10, 4, 3, 10, 3, 10, 9, 5, 9, 9, 10,
				9, 9, 9, 10 };
		int[] routing = new int[] { 4, 9, 10, 3, 10, 5, 4, 4, 5, 5, 4, 4, 5, 9, 5, 10, 4, 3, 10, 3, 10, 9, 5, 9, 9, 10,
				9, 9, 9, 10 };
		int[] srouting = new int[] { 5, 2, 10, 3, 10, 5, 2, 4, 4, 5, 2, 2, 3, 4, 5, 10, 2, 3, 10, 3, 10, 4, 2, 5, 4, 10,
				2, 4, 3, 10 };
		int[] nrouting = new int[] { 4, 9, 10, 3, 10, 5, 4, 4, 5, 5, 4, 3, 5, 9, 5, 10, 4, 3, 10, 10, 10, 9, 5, 9, 9,
				10, 9, 9, 9, 10 };
		/*
		 * for(int i=0;i<nrouting.length;i++){ int dec =srouting[i]; if (dec !=10)
		 * d.line(r.rx[i],r.ry[i], r.Nodes[dec][0], r.Nodes[dec][1]);
		 * 
		 * }
		 */

		d.show();
		// sleep(10);

		/*
		 * for ( int si=0; si<sites.length;si++) {
		 * 
		 * d.setFont(new Font("", Font.BOLD, 10)); d.textLeft(sites[si][0],
		 * sites[si][1], String.valueOf(si));
		 * 
		 * d.setFont(new Font("", Font.BOLD, 20)); d.text(35.0, 5.0,
		 * String.valueOf(networkUsage));
		 */
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadInstance ri = new ReadInstance(args[0]);

		ri.draw();

		/*
		 * for (int ic = 0; ic < rx.length; ic++) { // draw a circle for each node
		 * 
		 * d.filledCircle(rx[ic], ry[ic], 0.4); d.setFont(new Font("", Font.BOLD, 10));
		 * d.textLeft(rx[ic]+1, ry[ic]+1,String.valueOf(ic));
		 * 
		 * }
		 */

	}
}
