package jmetal.encodings.solutionType;

import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import jmetal.encodings.variable.BinaryInteger;

public class BinaryIntegerSolutionType extends SolutionType {
	public BinaryIntegerSolutionType(Problem problem) {
		super(problem);
	}

	public Variable[] createVariables() {
		Variable[] variables = new Variable[problem_.getNumberOfVariables()];

		for (int var = 0; var < problem_.getNumberOfVariables(); var++) {
			/*
			 * if (problem_.getPrecision() == null) { int [] precision = new
			 * int[problem_.getNumberOfVariables()] ; for (int i = 0; i <
			 * problem_.getNumberOfVariables(); i++) precision[i] =
			 * jmetal.encodings.variable.BinaryInteger.DEFAULT_PRECISION ;
			 * problem_.setPrecision(precision) ; }
			 */// if
			variables[var] = new BinaryInteger(problem_.getLength(var), (int) problem_.getLowerLimit(var),
					(int) problem_.getUpperLimit(var));
		} // for
		return variables;
	} // createVariables
}
