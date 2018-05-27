package jmetal.core;

import jmetal.util.JMException;

public interface AcceptHalfCooked {

	default SolutionSet execute(SolutionSet s) throws JMException, ClassNotFoundException {
		return null;
	};
}
