package jmetal.problems.carleton.FPP;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.util.JMException;

public class FogPlanningProblemBinary extends Problem {
	static final int[] fogprice = { 0, 67200, 120000, 170000, 250000 };
	static final int[] fogcpu = { 0, 90, 180, 360, 720 };
	static final int[] linkprice = { 0, 225, 1800, 18000 };
	static final int[] linkCp = { 0, 13107200, 125000000, 1250000000 };
	static final int rent = 1000;
	static final int est_size = 1;
	int clients_tem = -1;
	int fogs_tem = -1;
	public ReadInstance ri;

	public FogPlanningProblemBinary() {
		// TODO Auto-generated constructor stub
	}

	public FogPlanningProblemBinary(String solutionType) {
		this(solutionType, "30-10__ver1-try");
		// TODO Auto-generated constructor stub
	}

	public FogPlanningProblemBinary(String solutionType, String inputname) {
		ri = new ReadInstance(inputname);
		numberOfVariables_ = ri.clients + (ri.fogs * 2);
		numberOfObjectives_ = ri.objectives_;
		numberOfConstraints_ = 2 * ri.fogs;
		problemName_ = "FPP";
		upperLimit_ = new double[numberOfVariables_];
		lowerLimit_ = new double[numberOfVariables_];
		// length_ = new int[numberOfVariables_];

		clients_tem = ri.clients;
		fogs_tem = ri.fogs;
		for (int var = 0; var < clients_tem; var++) {
			lowerLimit_[var] = 0;
			upperLimit_[var] = ri.fogs;
		}
		for (int var = 0; var < ri.fogs; var++) {

			lowerLimit_[clients_tem + var] = 0;
			upperLimit_[clients_tem + var] = 4;
		}

		for (int var = 0; var < ri.fogs; var++) {
			lowerLimit_[clients_tem + fogs_tem + var] = 0;
			upperLimit_[clients_tem + fogs_tem + var] = 3;
		}
		solutionType_ = new IntSolutionType(this);
	}

	public void evaluate(Solution solution) throws JMException {
		double[] f = new double[numberOfObjectives_];
		// only care about five location to build fog hopefully;
		for (int locationnum = 0; locationnum < fogs_tem; locationnum++) {
			int deci = (int) solution.getDecisionVariables()[clients_tem + locationnum].getValue();
			int deciii = (int) solution.getDecisionVariables()[clients_tem + fogs_tem + locationnum].getValue();
			if (0 == deci) {
				continue;
			} else {
				f[0] += fogprice[deci];
				f[0] += rent;
				f[0] += linkprice[deciii];
			}
		}

		for (int clientnum = 0; clientnum < clients_tem; clientnum++) {

			int decii = (int) solution.getDecisionVariables()[clientnum].getValue();
			f[1] += ri.d_matrix[clientnum][decii];
		}

		solution.setObjective(0, f[0]);
		solution.setObjective(1, f[1]);
	}

	public void evaluateConstraints(Solution solution) throws JMException {
		double[] constraint = new double[this.getNumberOfConstraints()];

		for (int i = 0; i < fogs_tem; i++) {
			int decii = (int) solution.getDecisionVariables()[clients_tem + i].getValue();

			double totalDemand = 0.0;
			for (int j = 0; j < clients_tem; j++) {
				int deci = (int) solution.getDecisionVariables()[j].getValue();
				if (i == deci) {
					totalDemand += ri.cpu_demand[j];
				}
			}
			constraint[i] = fogcpu[decii] - totalDemand;
		}
		for (int i = 0; i < (fogs_tem); i++) {
			int decii = (int) solution.getDecisionVariables()[clients_tem + i].getValue();
			if (0 == decii) {
				continue;
			}
			int deciii = (int) solution.getDecisionVariables()[fogs_tem + clients_tem + i].getValue();
			double totalDemand = 0.0;
			for (int j = 0; j < clients_tem; j++) {

				int deci = (int) solution.getDecisionVariables()[j].getValue();
				if (i == deci) {
					totalDemand += 0.01 * ri.bandwidth_demand[j];
				}
			}
			constraint[i + fogs_tem] = linkCp[deciii] - totalDemand;
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
