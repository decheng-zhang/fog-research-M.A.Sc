//  NSGAIIStudy.java
//
//  Authors:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jmetal.experiments.studies;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import jmetal.core.Algorithm;
import jmetal.experiments.Experiment;
import jmetal.experiments.Settings;
import jmetal.experiments.settings.PSONSGAII_Settings;
import jmetal.experiments.settings.SMPSO_Settings;
import jmetal.experiments.util.Friedman;
import jmetal.util.JMException;

/**
 * Class implementing an example of experiment using NSGA-II as base algorithm.
 * The experiment consisting in studying the effect of the crossover probability
 * in NSGA-II.
 */
public class PSONSGAIIStudy extends Experiment {
	/**
	 * Configures the algorithms in each independent run
	 * 
	 * @param problemName
	 *            The problem to solve
	 * @param problemIndex
	 * @param algorithm
	 *            Array containing the algorithms to run
	 * @throws ClassNotFoundException
	 */
	public synchronized void algorithmSettings(String problemName, int problemIndex, Algorithm[] algorithm)
			throws ClassNotFoundException {
		try {
			int numberOfAlgorithms = algorithmNameList_.length;

			HashMap[] parameters = new HashMap[numberOfAlgorithms];
			HashMap[] prepara = new HashMap[numberOfAlgorithms];
			for (int i = 0; i < numberOfAlgorithms; i++) {
				parameters[i] = new HashMap();
				prepara[i] = new HashMap();
			} // for

			if (!(paretoFrontFile_[problemIndex] == null) && !paretoFrontFile_[problemIndex].equals("")) {
				for (int i = 0; i < numberOfAlgorithms; i++) {
					parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
					prepara[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
				}
			} // if

			for (int i = 0; i < 1; i++) {

				parameters[i].put("maxEvaluations_", 400);// 1300
				parameters[i].put("populationSize_", 1000);
				// double value
				prepara[i].put("maxIterations_", 1800);
				prepara[i].put("swarmSize_", 1000);
				prepara[i].put("archiveSize_", 1000);

			}

			// parameters[1].put("populationSize_", 100);
			// parameters[2].put("populationSize_", 100);

			// parameters[0].put("mutationProbability_", 1.0);
			/*
			 * parameters[1].put("crossoverDistributionIndex_", 40.0);
			 * parameters[2].put("crossoverDistributionIndex_", 60.0);
			 */

			// parameters[1].put("maxEvaluations_", 25000);
			// parameters[2].put("maxEvaluations_", 25000);
			// parameters[3].put("maxEvaluations_", 75000);

			/*
			 * if ((!paretoFrontFile_[problemIndex].equals("")) ||
			 * (paretoFrontFile_[problemIndex] == null)) { for (int i = 0; i <
			 * numberOfAlgorithms; i++) parameters[i].put("paretoFrontFile_",
			 * paretoFrontFile_[problemIndex]); }
			 */ // if

			for (int i = 0; i < numberOfAlgorithms; i++) {
				Algorithm p = new SMPSO_Settings(problemName).configure(prepara[i]);
				algorithm[i] = new PSONSGAII_Settings(p, problemName).configure(parameters[i]);
			}
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(PSONSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(PSONSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
		} catch (JMException ex) {
			Logger.getLogger(PSONSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
		}
	} // algorithmSettings

	public static void main(String[] args) throws JMException, IOException {
		PSONSGAIIStudy exp = new PSONSGAIIStudy(); // exp = experiment

		exp.experimentName_ = "Nov15n-PSONSGA-30test";
		String[] tmp = new String[1];
		/*
		 * for(int i=0;i<10;i++){ tmp[i]="eva_"+(100000+(i%5)*100); String
		 * t=((int)(i/5))<1?"ORG":"SS"; tmp[i]+="_"+t; } ;
		 */
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = "PSONSGAII-total-30";
		}
		exp.algorithmNameList_ = tmp;
		exp.problemList_ = new String[] {
				"FogPlanningProblem3010" };/*
											 * , "FogPlanningProblem3510", "FogPlanningProblem4010",
											 * "FogPlanningProblem4510", "FogPlanningProblem5010",
											 * "FogPlanningProblem5510", "FogPlanningProblem6010",
											 * "FogPlanningProblem6510", "FogPlanningProblem7010",
											 * "FogPlanningProblem7510", "FogPlanningProblem8010",
											 * "FogPlanningProblem8510", "FogPlanningProblem9010",
											 * "FogPlanningProblem9510"};
											 */
		/*
		 * exp.problemList_ = new String[] { "ZDT1", "ZDT2", "ZDT3", "ZDT4", "DTLZ1",
		 * "WFG2"} ;
		 */
		exp.paretoFrontFile_ = new String[] {
				"nove2/30-10.pf" };/*
									 * , "nove2/35-10.pf", "nove2/40-10.pf", "nove2/45-10.pf", "nove2/50-10.pf",
									 * "nove2/55-10.pf", "nove2/60-10.pf", "nove2/65-10.pf", "nove2/70-10.pf",
									 * "nove2/75-10.pf", "nove2/80-10.pf", "nove2/85-10.pf", "nove2/90-10.pf",
									 * "nove2/95-10.pf"};
									 */// {
		// "ZDT1.pf", "ZDT2.pf", "ZDT3.pf","ZDT4.pf", "DTLZ1.2D.pf", "WFG2.2D.pf"} ;
		exp.indicatorList_ = new String[] { "HV", "SPREAD", "EPSILON" };

		int numberOfAlgorithms = exp.algorithmNameList_.length;

		exp.experimentBaseDirectory_ = "/Users/qrafzv/Documents/workspace/FPP/" + exp.experimentName_;
		exp.paretoFrontDirectory_ = "/Users/qrafzv/Documents/workspace/jMetaltest";

		exp.algorithmSettings_ = new Settings[numberOfAlgorithms];

		exp.independentRuns_ = 10;

		exp.initExperiment();

		// Run the experiments
		int numberOfThreads;
		exp.runExperiment(numberOfThreads = 8);

		exp.generateQualityIndicators();

		// Generate latex tables (comment this sentence is not desired)
		exp.generateLatexTables();

		// Configure the R scripts to be generated
		int rows;
		int columns;
		String prefix;
		String[] problems;

		boolean notch;
		rows = 1;
		columns = 1;
		prefix = new String("carleton.FPP");
		problems = new String[] { "FogPlanningProblem3010" };/*
																 * , "FogPlanningProblem3510", "FogPlanningProblem4010",
																 * "FogPlanningProblem4510", "FogPlanningProblem5010",
																 * "FogPlanningProblem5510", "FogPlanningProblem6010",
																 * "FogPlanningProblem6510", "FogPlanningProblem7010",
																 * "FogPlanningProblem7510", "FogPlanningProblem8010",
																 * "FogPlanningProblem8510", "FogPlanningProblem9010",
																 * "FogPlanningProblem9510"} ;
																 */

		exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch = false, exp);
		exp.generateRWilcoxonScripts(problems, prefix, exp);

		/*
		 * rows = 2 ; columns = 3 ; prefix = new String("Problems"); problems = new
		 * String[]{"ZDT1", "ZDT2","ZDT3", "ZDT4", "DTLZ1", "WFG2"} ;
		 * 
		 * boolean notch ; exp.generateRBoxplotScripts(rows, columns, problems, prefix,
		 * notch = true, exp) ; exp.generateRWilcoxonScripts(problems, prefix, exp) ;
		 * 
		 */
		// Applying Friedman test
		Friedman test = new Friedman(exp);
		test.executeTest("EPSILON");
		test.executeTest("HV");
		test.executeTest("SPREAD");
	} // main
} // NSGAIIStudy
