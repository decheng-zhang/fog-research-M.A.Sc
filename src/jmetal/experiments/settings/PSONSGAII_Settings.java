package jmetal.experiments.settings;

import java.util.HashMap;
import java.util.Properties;

import jmetal.core.Algorithm;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.nsgaII.PSONSGAII;
import jmetal.operators.crossover.Crossover;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.Selection;
import jmetal.operators.selection.SelectionFactory;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

public class PSONSGAII_Settings extends Settings {

	public int populationSize_;
	public int maxEvaluations_;
	public double mutationProbability_;
	public double crossoverProbability_;
	public double mutationDistributionIndex_;
	public double crossoverDistributionIndex_;

	public Algorithm preprocessing;

	public PSONSGAII_Settings(Algorithm firstStep, String problem) {
		super(problem);
		preprocessing = firstStep;

		Object[] problemParams = { "Real" };
		try {
			problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
		} catch (JMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Default experiments.settings
		populationSize_ = 100;
		maxEvaluations_ = 2500;
		mutationProbability_ = 1.0 / problem_.getNumberOfVariables();
		crossoverProbability_ = 0.9;
		mutationDistributionIndex_ = 20.0;
		crossoverDistributionIndex_ = 20.0;
	} // NSGAII_Settings

	@Override
	public Algorithm configure() throws JMException {
		Algorithm algorithm;
		Selection selection;
		Crossover crossover;
		Mutation mutation;

		HashMap parameters; // Operator parameters

		// Creating the algorithm. There are two choices: NSGAII and its steady-
		// state variant ssNSGAII
		algorithm = new PSONSGAII(preprocessing, problem_);
		// algorithm = new ssNSGAII(problem_) ;

		// Algorithm parameters
		algorithm.setInputParameter("populationSize", populationSize_);
		algorithm.setInputParameter("maxEvaluations", maxEvaluations_);

		// Mutation and Crossover for Real codification
		parameters = new HashMap();
		parameters.put("probability", crossoverProbability_);
		parameters.put("distributionIndex", crossoverDistributionIndex_);
		crossover = CrossoverFactory.getCrossoverOperator("SBXCrossover", parameters);

		parameters = new HashMap();
		parameters.put("probability", mutationProbability_);
		parameters.put("distributionIndex", mutationDistributionIndex_);
		mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);

		// Selection Operator
		parameters = null;
		selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters);

		// Add the operators to the algorithm
		algorithm.addOperator("crossover", crossover);
		algorithm.addOperator("mutation", mutation);
		algorithm.addOperator("selection", selection);

		return algorithm;
	}

	@Override
	public Algorithm configure(Properties configuration) throws JMException {
		Algorithm algorithm;
		Selection selection;
		Crossover crossover;
		Mutation mutation;

		HashMap parameters; // Operator parameters

		// Creating the algorithm. There are two choices: NSGAII and its steady-
		// state variant ssNSGAII
		algorithm = new PSONSGAII(preprocessing, problem_);
		// algorithm = new ssNSGAII(problem_) ;

		// Algorithm parameters
		populationSize_ = Integer
				.parseInt(configuration.getProperty("populationSize", String.valueOf(populationSize_)));
		maxEvaluations_ = Integer
				.parseInt(configuration.getProperty("maxEvaluations", String.valueOf(maxEvaluations_)));
		algorithm.setInputParameter("populationSize", populationSize_);
		algorithm.setInputParameter("maxEvaluations", maxEvaluations_);

		// Mutation and Crossover for Real codification
		crossoverProbability_ = Double
				.parseDouble(configuration.getProperty("crossoverProbability", String.valueOf(crossoverProbability_)));
		crossoverDistributionIndex_ = Double.parseDouble(
				configuration.getProperty("crossoverDistributionIndex", String.valueOf(crossoverDistributionIndex_)));
		parameters = new HashMap();
		parameters.put("probability", crossoverProbability_);
		parameters.put("distributionIndex", crossoverDistributionIndex_);
		crossover = CrossoverFactory.getCrossoverOperator("SBXCrossover", parameters);

		mutationProbability_ = Double
				.parseDouble(configuration.getProperty("mutationProbability", String.valueOf(mutationProbability_)));
		mutationDistributionIndex_ = Double.parseDouble(
				configuration.getProperty("mutationDistributionIndex", String.valueOf(mutationDistributionIndex_)));
		parameters = new HashMap();
		parameters.put("probability", mutationProbability_);
		parameters.put("distributionIndex", mutationDistributionIndex_);
		mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);

		// Selection Operator
		parameters = null;
		selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters);

		// Add the operators to the algorithm
		algorithm.addOperator("crossover", crossover);
		algorithm.addOperator("mutation", mutation);
		algorithm.addOperator("selection", selection);

		return algorithm;
	}
}
