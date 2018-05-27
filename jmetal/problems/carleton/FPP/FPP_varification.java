package jmetal.problems.carleton.FPP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import jmetal.core.Problem;
import jmetal.problems.mqap.ReadInstance;

public class FPP_varification {
	int decisionVariable[][] = null;
	double objValue[][] = null;
	int lineCnt = -1;
	FogPlanningProblem problem;

	public FPP_varification(Problem problem) {
		this.problem = (FogPlanningProblem) problem;
		decisionVariable = new int[1000][problem.getNumberOfVariables()];
		objValue = new double[1000][2];
		loadOBJResult("FUN-cplex");
		loadexpResult("VAR-cplex");

		verifyProcess();
	}

	private void verifyProcess() {
		for (int i = 0; i < lineCnt; i++) {
			int total = 0;
			for (int j = problem.getNumberOfVariables() - 1; j > problem.clients_tem + problem.fogs_tem - 1; j--) {
				int t = decisionVariable[i][j - problem.fogs_tem];
				int l = decisionVariable[i][j];
				if (0 != t) {
					total += FogPlanningProblem.rent;
				}
				total += FogPlanningProblem.linkprice[l];
				total += FogPlanningProblem.fogprice[t];
			}
			double totalD = 0.0;
			for (int j = 0; j < problem.clients_tem; j++) {
				int z = decisionVariable[i][j];
				totalD += ((FogPlanningProblem) problem).ri.d_matrix[j][z];
			}

			if (objValue[i][0] != total)
				Logger.getLogger(ReadInstance.class.getName()).log(Level.SEVERE,
						"test " + (i + 1) + "did't pass: " + (objValue[i][0]) + "  " + (total));
			if (objValue[i][1] != totalD)
				Logger.getLogger(ReadInstance.class.getName()).log(Level.SEVERE,
						"test " + (i + 1) + "delay did't pass: " + (objValue[i][1]) + "  " + (totalD));
			;

			int[] cpu_offers = new int[problem.fogs_tem];
			for (int j = 0; j < problem.clients_tem; j++) {
				int t = decisionVariable[i][j];
				if (t == problem.fogs_tem)
					continue;
				cpu_offers[t] += problem.ri.cpu_demand[j];
			}
			boolean noexceed = true;
			for (int z = 0; z < problem.fogs_tem; z++) {
				boolean tmp = (cpu_offers[z] <= FogPlanningProblem.fogcpu[decisionVariable[i][problem.clients_tem
						+ z]]);
				if (!tmp) {
					Logger.getLogger(ReadInstance.class.getName()).log(Level.SEVERE,
							"test" + (i + 1) + "The" + (z + 1) + "-th demand violation with");
					String finalnotes = cpu_offers[z] + "   "
							+ (FogPlanningProblem.fogcpu[decisionVariable[i][problem.clients_tem + z]]);
					System.out.println(finalnotes);
					System.exit(1);
				}
				noexceed = (noexceed && tmp);
			}
			if (!noexceed)
				Logger.getLogger(ReadInstance.class.getName()).log(Level.SEVERE, "test" + (i + 1) + "demand violation");
			else
				Logger.getLogger(ReadInstance.class.getName()).log(Level.SEVERE, "test passed");
		}

	}

	private void loadOBJResult(String path) {
		try {
			File f = new File(path);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line;
			line = br.readLine();
			int i = 0;
			while (!((line == null) || line.isEmpty())) {
				StringTokenizer st = new StringTokenizer(line);
				int j = 0;
				while (st.hasMoreTokens()) {
					objValue[i][j] = Double.valueOf(st.nextToken());
					j++;
				}
				line = br.readLine();
				i++;
			}
			lineCnt = i;

		} catch (IOException ex) {
			Logger.getLogger(ReadInstance.class.getName()).log(Level.SEVERE, "Error reading from file", ex);
		}
	}

	private void loadexpResult(String path) {
		try {
			File f = new File(path);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line;
			line = br.readLine();
			int i = 0;
			while (!((line == null) || line.isEmpty())) {
				StringTokenizer st = new StringTokenizer(line);
				int j = 0;
				while (st.hasMoreTokens()) {
					decisionVariable[i][j] = Integer.valueOf(st.nextToken());
					j++;
				}
				line = br.readLine();
				i++;
			}
			if (lineCnt != i)
				System.out.println("different number of FUN and VAR record");

		} catch (IOException ex) {
			Logger.getLogger(ReadInstance.class.getName()).log(Level.SEVERE, "Error reading from file", ex);
		}
	}

	public static void main(String[] args) {
		FogPlanningProblem p = new FogPlanningProblem("int");
		new FPP_varification(p);// TODO Auto-generated method stub

	}

}
