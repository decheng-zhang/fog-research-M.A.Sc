package jmetal.metaheuristics.nsgaII;

import jmetal.core.Algorithm;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.util.JMException;

public class PSONSGAII extends NSGAII {
	Algorithm preprocessing;

	public PSONSGAII(Algorithm pre, Problem problem) {
		this(problem);
		preprocessing = pre;
	}

	public PSONSGAII(Problem problem) {
		super(problem);

		// TODO Auto-generated constructor stub
	}

	@Override
	public SolutionSet execute() throws JMException, ClassNotFoundException {

		// initTime = System.currentTimeMillis();
		SolutionSet midset = preprocessing.execute();// .feasibleEnforcement();
		// System.out.println("can be don");
		super.primalSolutionSet = midset;
		// long nameTime = System.currentTimeMillis();
		// midset.printFeasibleFUN("INI-"+(nameTime));
		SolutionSet population = super.execute();
		// population.printFeasibleFUN("beta-"+(nameTime));
		// System.out.println("ccan be don");
		// estimatedTime = System.currentTimeMillis() - initTime;
		return population;
	}
}
