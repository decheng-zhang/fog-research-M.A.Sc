package jmetal.problems.carleton.FPP;

import jmetal.core.Problem;
import jmetal.core.Solution;
import jmetal.encodings.solutionType.IntSolutionType;
import jmetal.util.JMException;

public class FogPlanningProblem extends Problem {
	static String VERSION_INDEX = "1";
	static final int[] fogprice = { 0, 67200, 120000, 170000, 250000 };
	static final int[] fogmem = { 0, 480, 800, 1600, 3200 };
	static final int[] fogcpu = { 0, 90, 180, 360, 720 };
	static final int[] linkprice = { 0, 225000, 1800000, 18000000 };
	static final int[] linkCp = { 0, 13107200, 125000000, 1250000000 };
	static final int rent = 1000;
	static final int est_size = 1;
	int clients_tem = -1;
	int fogs_tem = -1;
	double[] constraint_max;
	double[] constraint_min;
	public ReadInstance ri;

	public FogPlanningProblem() {
		// TODO Auto-generated constructor stub
	}

	public FogPlanningProblem(String solutionType) {
		this(solutionType, "90-10__ver1-try");
		// TODO Auto-generated constructor stub
	}

	public FogPlanningProblem(String solutionType, String inputname) {
		ri = new ReadInstance(inputname);
		numberOfVariables_ = ri.clients + (ri.fogs * 2);
		numberOfObjectives_ = ri.objectives_;
		numberOfConstraints_ = 3 * ri.fogs;
		constraint_max = new double[numberOfConstraints_];
		constraint_min = new double[numberOfConstraints_];
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
		for (int var = 0; var < fogs_tem; var++) {

			lowerLimit_[clients_tem + var] = 0;
			upperLimit_[clients_tem + var] = fogcpu.length - 1;
		}

		for (int var = 0; var < fogs_tem; var++) {
			lowerLimit_[clients_tem + fogs_tem + var] = 0;
			upperLimit_[clients_tem + fogs_tem + var] = linkCp.length - 1;
		}
		solutionType_ = new IntSolutionType(this);
	}

	public void evaluate(Solution solution) throws JMException {
		double[] f = new double[numberOfObjectives_];
		// only care about five location to build fog hopefully;
		// System.out.println("AAAAAAAAAAAAAAAA"+solution.toVariableString());
		double[] totalCPUDemands = new double[fogs_tem + 1];
		double[] totalMEMDemands = new double[fogs_tem + 1];
		double[] totalLinkDemands = new double[fogs_tem + 1];
		for (int j = 0; j < clients_tem; j++) {
			int dec = (int) solution.getDecisionVariables()[j].getValue();
			totalCPUDemands[dec] += ri.cpu_demand[j];
			totalMEMDemands[dec] += ri.mem_demand[j];
			totalLinkDemands[dec] += 0.01 * ri.bandwidth_demand[j] + 1.0;
			// totalDemand += 0.01 *ri.bandwidth_demand[j]+1.0;//KB
		}

		for (int locationnum = 0; locationnum < fogs_tem; locationnum++) {
			/*
			 * int deci=(int)
			 * solution.getDecisionVariables()[clients_tem+locationnum].getValue(); //int
			 * deciii = (int)
			 * solution.getDecisionVariables()[clients_tem+fogs_tem+locationnum].getValue();
			 * 
			 * if(0==deci){
			 * solution.getDecisionVariables()[clients_tem+fogs_tem+locationnum].setValue(0.
			 * 0); continue;
			 * 
			 * }else{ double totalDemand = 0.0; for(int j=0;j<clients_tem;j++){
			 * 
			 * 
			 * if(locationnum==deci){ totalDemand += 0.01 *ri.bandwidth_demand[j]+1.0;//KB }
			 * }
			 */

			int cpuchoice = Math.min(fogcpu.length - 1, greaterEqual(fogcpu, (int) totalCPUDemands[locationnum]));
			int memchoice = Math.min(fogmem.length - 1, greaterEqual(fogmem, (int) totalMEMDemands[locationnum]));
			cpuchoice = Math.max(cpuchoice, memchoice);
			int linkchoice = Math.min(linkCp.length - 1, greaterEqual(linkCp, (int) totalLinkDemands[locationnum]));
			solution.getDecisionVariables()[clients_tem + locationnum].setValue((double) cpuchoice);
			solution.getDecisionVariables()[clients_tem + fogs_tem + locationnum].setValue((double) linkchoice);
			f[0] += fogprice[cpuchoice]; // fog machine buying cost;
			f[0] += (cpuchoice == 0) ? 0 : rent; // location rental fee;
			f[0] += linkprice[linkchoice];// link building cost;
			// f[0]+= totalCPUDemands[locationnum]*1.0;//electricity bill;
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

		// cpu constraints
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
		// mem constraint
		for (int i = 0; i < fogs_tem; i++) {
			int decii = (int) solution.getDecisionVariables()[clients_tem + i].getValue();

			double totalDemand = 0.0;
			for (int j = 0; j < clients_tem; j++) {
				int deci = (int) solution.getDecisionVariables()[j].getValue();
				if (i == deci) {
					totalDemand += ri.mem_demand[j];
				}
			}
			constraint[i] = fogmem[decii] - totalDemand;
		}
		// bandwidth constraint
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
					totalDemand += 0.01 * ri.bandwidth_demand[j] + 1.0;
				}
			}
			constraint[i + fogs_tem] = (double) linkCp[deciii] - totalDemand;
		}

		// constraint normalization
		double total = 0.0;
		int number = 0;
		for (int i = 0; i < this.getNumberOfConstraints(); i++)
			if (constraint[i] < 0.0) {
				if (constraint[i] < constraint_min[i]) {
					constraint_min[i] = constraint[i];
				}
				total += normalized(constraint[i], constraint_min[i]);
				number++;
			}

		solution.setOverallConstraintViolation(total);
		solution.setNumberOfViolatedConstraint(number);
	}

	private double normalized(double x, double min) {
		return (x) / (-1.0 * min);
	}

	// upper bound function
	private int greaterEqual(int[] pricetags, int target) {
		int low = 0, high = pricetags.length;

		while (low < high) {
			int mid = low + ((high - low) >> 1);
			if (pricetags[mid] < target) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}
		return low;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
