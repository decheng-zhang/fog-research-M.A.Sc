//  StandardStudy2.java
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
import jmetal.experiments.settings.NSGAII_Settings;
import jmetal.experiments.settings.PSONSGAII_Settings;
import jmetal.experiments.settings.SMPSO_Settings;
import jmetal.util.JMException;

/**
 * Class implementing a typical experimental study. Five algorithms are compared
 * when solving the ZDT, DTLZ, and WFG benchmarks, and the hypervolume, spread
 * and additive epsilon indicators are used for performance assessment. In this
 * experiment, we assume that the true Pareto fronts are unknown, so they must
 * be calculated automatically.
 */
public class StandardStudy2 extends Experiment {

	/**
	 * Configures the algorithms in each independent run
	 * 
	 * @param problemName
	 *            The problem to solve
	 * @param problemIndex
	 * @throws ClassNotFoundException
	 */
	public void algorithmSettings(String problemName, int problemIndex, Algorithm[] algorithm)
			throws ClassNotFoundException {
		try {
			int numberOfAlgorithms = algorithmNameList_.length;

			HashMap[] parameters = new HashMap[numberOfAlgorithms];

			for (int i = 0; i < numberOfAlgorithms; i++) {
				parameters[i] = new HashMap();

			}
			// for

			parameters[0].put("maxEvaluations_", 10000);
			parameters[1].put("maxIterations_", 10000);
			parameters[2].put("maxEvaluations_", 10000);
			// parameters[2].put("maxEvaluations_", 1000);
			HashMap paraaux = (new HashMap());
			paraaux.put("maxIterations_", 10000);

			parameters[0].put("populationSize_", 100);

			parameters[1].put("swarmSize_", 100);
			parameters[1].put("archiveSize_", 100);
			parameters[2].put("populationSize_", 100);

			// parameters[3].put("populationSize_", 1000);
			paraaux.put("swarmSize_", 100);
			paraaux.put("archiveSize_", 100);

			if (!(paretoFrontFile_[problemIndex] == null) && !paretoFrontFile_[problemIndex].equals("")) {
				for (int i = 0; i < numberOfAlgorithms; i++)
					parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
				paraaux.put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
			} // if

			algorithm[0] = new NSGAII_Settings(problemName).configure(parameters[0]);
			algorithm[1] = new SMPSO_Settings(problemName).configure(parameters[1]);
			// algorithm[2] = new SPEA2_Settings(problemName).configure(parameters[2]);
			Algorithm p = new SMPSO_Settings(problemName).configure(paraaux);
			algorithm[2] = new PSONSGAII_Settings(p, problemName).configure(parameters[2]);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(StandardStudy2.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(StandardStudy2.class.getName()).log(Level.SEVERE, null, ex);
		} catch (JMException ex) {
			Logger.getLogger(StandardStudy2.class.getName()).log(Level.SEVERE, null, ex);
		}
	} // algorithmSettings

	/**
	 * Main method
	 * 
	 * @param args
	 * @throws JMException
	 * @throws IOException
	 */
	public static void main(String[] args) throws JMException, IOException {
		StandardStudy2 exp = new StandardStudy2();

		exp.experimentName_ = "NOV26-total-comparison-big3-10000iter";
		exp.algorithmNameList_ = new String[] { "NSGAII", "SMPSO", "PSONSGAII" };// , "SMPSO"};
		exp.problemList_ = new String[] { "FogPlanningProblem3010", "FogPlanningProblem3510", "FogPlanningProblem4010",
				"FogPlanningProblem4510", "FogPlanningProblem5010", "FogPlanningProblem5510", "FogPlanningProblem6010",
				"FogPlanningProblem6510", "FogPlanningProblem7010", "FogPlanningProblem7510", "FogPlanningProblem8010",
				"FogPlanningProblem8510", "FogPlanningProblem9010", "FogPlanningProblem9510"
				/*
				 * "FogPlanningProblem0505", "FogPlanningProblem1005", "FogPlanningProblem1505",
				 * "FogPlanningProblem2005", "FogPlanningProblem2505", "FogPlanningProblem3005",
				 * "FogPlanningProblem3505", "FogPlanningProblem4005", "FogPlanningProblem4505",
				 * "FogPlanningProblem5005", "FogPlanningProblem5505", "FogPlanningProblem6005"
				 * /* "FogPlanningProblem10010", "FogPlanningProblem20010",
				 * "FogPlanningProblem30010", "FogPlanningProblem40010",
				 * "FogPlanningProblem50010"
				 */

		};/*
			 * {"ZDT1", "ZDT2","ZDT3", "ZDT4","ZDT6",
			 * "WFG1","WFG2","WFG3","WFG4","WFG5","WFG6", "WFG7","WFG8","WFG9",
			 * "DTLZ1","DTLZ2","DTLZ3","DTLZ4"}; ,"DTLZ5", "DTLZ6","DTLZ7"};
			 */
		exp.paretoFrontFile_ = new String[14];/*
												 * "nove2/30-10.pf", "nove2/35-10.pf", "nove2/40-10.pf",
												 * "nove2/45-10.pf", "nove2/50-10.pf", "nove2/55-10.pf",
												 * "nove2/60-10.pf", "nove2/65-10.pf", "nove2/70-10.pf",
												 * "nove2/75-10.pf", "nove2/80-10.pf", "nove2/85-10.pf",
												 * "nove2/90-10.pf","nove2/95-10.pf" /*"nove2/60-10.pf" "nove3/5-5.pf",
												 * "nove3/10-5.pf", "nove3/15-5.pf", "nove3/20-5.pf", "nove3/25-5.pf",
												 * "nove3/30-5.pf", "nove3/35-5.pf", "nove3/40-5.pf", "nove3/45-5.pf",
												 * "nove3/50-5.pf", "nove3/55-5.pf", "nove3/60-5.pf" "nove3/100-10.pf",
												 * "nove3/200-10.pf", "nove3/300-10.pf", "nove3/400-10.pf",
												 * "nove3/500-10.pf"
												 */

		// Space allocation for 18 fronts

		exp.indicatorList_ = new String[] { "HV", "IGD", "EPSILON", "SPREAD" };

		int numberOfAlgorithms = exp.algorithmNameList_.length;

		exp.experimentBaseDirectory_ = "/Users/qrafzv/Documents/workspace/FPP/" + exp.experimentName_;
		exp.paretoFrontDirectory_ = "/Users/qrafzv/Documents/workspace/jMetaltest"; // This directory must be empty

		exp.algorithmSettings_ = new Settings[numberOfAlgorithms];

		exp.independentRuns_ = 5;

		exp.initExperiment();

		// Run the experiments
		int numberOfThreads;
		exp.runExperiment(numberOfThreads = 8);
		exp.generateQualityIndicators();

		// Generate latex tables
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
		problems = new String[] { "FogPlanningProblem3010", "FogPlanningProblem3510", "FogPlanningProblem4010",
				"FogPlanningProblem4510", "FogPlanningProblem5010", "FogPlanningProblem5510", "FogPlanningProblem6010",
				"FogPlanningProblem6510", "FogPlanningProblem7010", "FogPlanningProblem7510", "FogPlanningProblem8010",
				"FogPlanningProblem8510", "FogPlanningProblem9010",
				"FogPlanningProblem9510"/*
										 * "FogPlanningProblem0505", "FogPlanningProblem1005", "FogPlanningProblem1505",
										 * "FogPlanningProblem2005", "FogPlanningProblem2505", "FogPlanningProblem3005",
										 * "FogPlanningProblem3505", "FogPlanningProblem4005", "FogPlanningProblem4505",
										 * "FogPlanningProblem5005", "FogPlanningProblem5505",
										 * "FogPlanningProblem6005"/* "FogPlanningProblem10010",
										 * "FogPlanningProblem20010", "FogPlanningProblem30010",
										 * "FogPlanningProblem40010", "FogPlanningProblem50010"
										 */
		};

		exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch = false, exp);
		exp.generateRWilcoxonScripts(problems, prefix, exp);

		// Configuring scripts for ZDT
		/*
		 * rows = 3 ; columns = 2 ; prefix = new String("ZDT"); problems = new
		 * String[]{"ZDT1", "ZDT2","ZDT3", "ZDT4","ZDT6"} ;
		 * 
		 * exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch = false,
		 * exp) ; exp.generateRWilcoxonScripts(problems, prefix, exp) ;
		 * 
		 * // Configure scripts for DTLZ rows = 3 ; columns = 3 ; prefix = new
		 * String("DTLZ"); problems = new
		 * String[]{"DTLZ1","DTLZ2","DTLZ3","DTLZ4","DTLZ5", "DTLZ6","DTLZ7"} ;
		 * 
		 * exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch=false,
		 * exp) ; exp.generateRWilcoxonScripts(problems, prefix, exp) ;
		 * 
		 * // Configure scripts for WFG rows = 3 ; columns = 3 ; prefix = new
		 * String("WFG"); problems = new
		 * String[]{"WFG1","WFG2","WFG3","WFG4","WFG5","WFG6", "WFG7","WFG8","WFG9"} ;
		 * 
		 * exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch=false,
		 * exp) ; exp.generateRWilcoxonScripts(problems, prefix, exp) ;
		 */
		// Applying Friedman test
		/*
		 * Friedman test = new Friedman(exp); test.executeTest("EPSILON");
		 * test.executeTest("HV"); test.executeTest("SPREAD");
		 */
	} // main
} // StandardStudy2
