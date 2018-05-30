package jmetal.problems.carleton.FPP;

import java.util.Arrays;
import java.util.Random;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.encodings.variable.Binary;
import jmetal.util.JMException;

public class Ten2FiveProblem extends Problem {
	static final int[] fogprice = { 0, 67200, 120000, 170000, 250000 };
	static final int[] fogcpu = { 0, 90, 180, 360, 720 };
	static final int[] linkprice = { 0, 1000, 10000, 100000 };
	static final int[] linkCp = { 0, 90, 180, 360, 720 };
	static final int rent = 1000;
	static int[] cpu_demand = new int[10];
	static int[] bandwidth_demand = new int[10];
	static double[][] d_matrix = new double[10][6];
	{

		Random seed = new Random();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				d_matrix[i][j] = seed.nextDouble() * 50 + 20;
			}
			d_matrix[i][5] = seed.nextDouble() * 50 + 20 + 900;
			cpu_demand[i] = seed.nextInt(50) + 1;
			bandwidth_demand[i] = seed.nextInt(50) + 1;
		}
		System.out.println("cpu-demand is " + Arrays.toString(cpu_demand));
		System.out.println("bandwidth-demand is " + Arrays.toString(bandwidth_demand));
	}

	public Ten2FiveProblem(String solutionType) throws ClassNotFoundException {
		this(solutionType, 20);
	}

	public Ten2FiveProblem(String solutionType, Integer numberOfVariables) {
		numberOfVariables_ = numberOfVariables;
		numberOfObjectives_ = 2;
		numberOfConstraints_ = 10;
		problemName_ = "Ten2Five";
		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];
		// length_ = new int[numberOfVariables_];
		for (int var = 0; var < 10; var++) {
			lowerLimit_[var] = 0;
			upperLimit_[var] = 5;
		}
		for (int var = 10; var < numberOfVariables_; var++) {
			lowerLimit_[var] = 0;
			upperLimit_[var] = 4;
		}
		for (int var = 15; var < 20; var++) {
			lowerLimit_[var] = 0;
			upperLimit_[var] = 3;
		}
		solutionType_ = new IntSolutionType(this);
	}

	public void evaluate(Solution solution) throws JMException {
		double[] f = new double[numberOfObjectives_];
		// only care about five location to build fog hopefully;
		for (int locationnum = 0; locationnum < 5; locationnum++) {
			int deci = (int) solution.getDecisionVariables()[10 + locationnum].getValue();
			int deciii = (int) solution.getDecisionVariables()[15 + locationnum].getValue();
			if (0 == deci)
				f[0] += rent;

			f[0] += fogprice[deci];
			f[0] += linkprice[deciii];
		}

		for (int clientnum = 0; clientnum < 10; clientnum++) {

			int decii = (int) solution.getDecisionVariables()[clientnum].getValue();
			f[1] += d_matrix[clientnum][decii];
		}

		solution.setObjective(0, f[0]);
		solution.setObjective(1, f[1]);
	}

	public void evaluateConstraints(Solution solution) throws JMException {
		double[] constraint = new double[this.getNumberOfConstraints()];

		for (int i = 0; i < 5; i++) {
			int decii = (int) solution.getDecisionVariables()[10 + i].getValue();
			double totalDemand = 0.0;
			for (int j = 0; j < 10; j++) {
				int deci = (int) solution.getDecisionVariables()[j].getValue();
				if (i == deci) {
					totalDemand += cpu_demand[j];
				}
			}
			constraint[i] = fogcpu[decii] - totalDemand;
		}
		for (int i = 0; i < 5; i++) {
			int deciii = (int) solution.getDecisionVariables()[15 + i].getValue();
			double totalDemand = 0.0;
			for (int j = 0; j < 10; j++) {
				int deci = (int) solution.getDecisionVariables()[j].getValue();
				if (i == deci) {
					totalDemand += bandwidth_demand[j];
				}
			}
			constraint[i + 5] = linkCp[deciii] - totalDemand;
		}

		double total = 0.0;
		int number = 0;
		for (int i = 0; i < this.getNumberOfConstraints(); i++)
			if (constraint[i] < 0.0) {
				total += constraint[i];
				number++;
			}

		solution.setOverallConstraintViolation(total);
		solution.setNumberOfViolatedConstraint(number);
	}

	private int u(Binary variable, int idx) {
		return variable.bits_.get(idx) ? 1 : 0;
	}
}
