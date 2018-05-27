package jmetal.experiments.settings;

import java.util.HashMap;
import java.util.Properties;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.nsgaII.NSGAII;
import jmetal.operators.crossover.Crossover;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.Selection;
import jmetal.operators.selection.SelectionFactory;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

public class NSGAIIInteger_Settings extends Settings {

	public int populationSize_;
	public int maxEvaluations_;

	public double mutationProbability_;
	public double crossoverProbability_;

	/**
	 * Constructor
	 */
	public NSGAIIInteger_Settings(String problem) {
		super(problem);

		Object[] problemParams = { "BinaryInteger" };
		try {
			problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
		} catch (JMException e) {
			e.printStackTrace();
		}

		// Default experiments.settings
		populationSize_ = 100;
		maxEvaluations_ = 25000;

		mutationProbability_ = 1.0 / problem_.getNumberOfBits();
		crossoverProbability_ = 0.9;
	} // NSGAIIBinary_Settings

	/**
	 * Configure NSGAII with user-defined parameter experiments.settings
	 * 
	 * @return A NSGAII algorithm object
	 * @throws jmetal.util.JMException
	 */
	public Algorithm configure() throws JMException {
		Algorithm algorithm;
		Operator selection;
		Operator crossover;
		Operator mutation;

		HashMap parameters; // Operator parameters

		// Creating the problem
		algorithm = new NSGAII(problem_);

		// Algorithm parameters
		algorithm.setInputParameter("populationSize", populationSize_);
		algorithm.setInputParameter("maxEvaluations", maxEvaluations_);

		// Mutation and Crossover Binary codification
		parameters = new HashMap();
		parameters.put("probability", crossoverProbability_);
		crossover = CrossoverFactory.getCrossoverOperator("SinglePointCrossover", parameters);

		parameters = new HashMap();
		parameters.put("probability", mutationProbability_);
		mutation = MutationFactory.getMutationOperator("BitFlipMutation", parameters);

		// Selection Operator
		parameters = null;
		selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters);

		// Add the operators to the algorithm
		algorithm.addOperator("crossover", crossover);
		algorithm.addOperator("mutation", mutation);
		algorithm.addOperator("selection", selection);

		return algorithm;
	} // configure

	/**
	 * Configure NSGAII with user-defined parameter experiments.settings
	 * 
	 * @return A NSGAII algorithm object
	 */
	@Override
	public Algorithm configure(Properties configuration) throws JMException {
		Algorithm algorithm;
		Selection selection;
		Crossover crossover;
		Mutation mutation;

		HashMap parameters; // Operator parameters

		// Creating the algorithm.
		algorithm = new NSGAII(problem_);

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
		parameters = new HashMap();
		parameters.put("probability", crossoverProbability_);
		crossover = CrossoverFactory.getCrossoverOperator("SinglePointCrossover", parameters);

		mutationProbability_ = Double
				.parseDouble(configuration.getProperty("mutationProbability", String.valueOf(mutationProbability_)));
		parameters = new HashMap();
		parameters.put("probability", mutationProbability_);
		mutation = MutationFactory.getMutationOperator("BitFlipMutation", parameters);

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
