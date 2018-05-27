//  InvertedGenerationalDistance.java
//
//  Author:
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

package jmetal.qualityIndicator;

/**
 * This class implements the inverted generational distance metric. It can be
 * used also as a command line by typing: "java
 * jmetal.qualityIndicator.InvertedGenerationalDistance <solutionFrontFile>
 * <trueFrontFile> <getNumberOfObjectives>" Reference: Van Veldhuizen, D.A.,
 * Lamont, G.B.: Multiobjective Evolutionary Algorithm Research: A History and
 * Analysis. Technical Report TR-98-03, Dept. Elec. Comput. Eng., Air Force
 * Inst. Technol. (1998)
 */
public class InvertedGenerationalDistance {
	public jmetal.qualityIndicator.util.MetricsUtil utils_; // utils_ is used to access to the
	// MetricsUtil funcionalities

	static final double pow_ = 2.0; // pow. This is the pow used for the
									// distances

	/**
	 * Constructor. Creates a new instance of the generational distance metric.
	 */
	public InvertedGenerationalDistance() {
		utils_ = new jmetal.qualityIndicator.util.MetricsUtil();
	} // GenerationalDistance

	/**
	 * Returns the inverted generational distance value for a given front
	 * 
	 * @param front
	 *            The front
	 * @param trueParetoFront
	 *            The true pareto front
	 */
	public double invertedGenerationalDistance(double[][] front, double[][] trueParetoFront, int numberOfObjectives) {

		/**
		 * Stores the maximum values of true pareto front.
		 */
		double[] maximumValue;

		/**
		 * Stores the minimum values of the true pareto front.
		 */
		double[] minimumValue;

		/**
		 * Stores the normalized front.
		 */
		double[][] normalizedFront;

		/**
		 * Stores the normalized true Pareto front.
		 */
		double[][] normalizedParetoFront;

		// STEP 1. Obtain the maximum and minimum values of the Pareto front
		maximumValue = utils_.getMaximumValues(trueParetoFront, numberOfObjectives);
		minimumValue = utils_.getMinimumValues(trueParetoFront, numberOfObjectives);

		// STEP 2. Get the normalized front and true Pareto fronts
		normalizedFront = utils_.getNormalizedFront(front, maximumValue, minimumValue);
		normalizedParetoFront = utils_.getNormalizedFront(trueParetoFront, maximumValue, minimumValue);

		// STEP 3. Sum the distances between each point of the true Pareto front and
		// the nearest point in the true Pareto front
		double sum = 0.0;
		for (double[] aNormalizedParetoFront : normalizedParetoFront)
			sum += Math.pow(utils_.distanceToClosedPoint(aNormalizedParetoFront, normalizedFront), pow_);

		// STEP 4. Obtain the sqrt of the sum
		sum = Math.pow(sum, 1.0 / pow_);

		// STEP 5. Divide the sum by the maximum number of points of the front
		double generationalDistance = sum / normalizedParetoFront.length;

		return generationalDistance;
	} // generationalDistance

	/**
	 * This class can be invoqued from the command line. Two params are required: 1)
	 * the name of the file containing the front, and 2) the name of the file
	 * containig the true Pareto front
	 **/
	public static void main(String args[]) {
		String[] filename = new String[] { "nove7/nov26-smallonefour_5-5__versmall4-try_time_459.pf1",
				"nove7/nov26-smallonefour_10-5__versmall4-try_time_320.pf1",
				"nove7/nov26-smallonefour_15-5__versmall4-try_time_283.pf1",
				"nove7/nov26-smallonefour_20-5__versmall4-try_time_250.pf1",
				"nove7/nov26-smallonefour_25-5__versmall4-try_time_934.pf1",
				"nove7/nov26-smallonefour_30-5__versmall4-try_time_4088.pf1",
				"nove7/nov26-smallonefour_35-5__versmall4-try_time_1521.pf1",
				"nove7/nov26-smallonefour_40-5__versmall4-try_time_549.pf1",
				"nove7/nov26-smallonefour_45-5__versmall4-try_time_2097.pf1",

				"nove7/nov26-smallonefour_50-5__versmall4-try_time_874.pf1",
				"nove7/nov26-smallonefour_55-5__versmall4-try_time_1251.pf1",
				"nove7/nov26-smallonefour_60-5__versmall4-try_time_877.pf1",
				"nove7/nov25_30-10__ver4-try_time_7242782.pf1", "nove7/nov25_35-10__ver4-try_time_3958.pf1",
				"nove7/nov25_40-10__ver4-try_time_37319.pf1", "nove7/nov25_45-10__ver4-try_time_2909.pf1",
				"nove7/nov25_50-10__ver4-try_time_8887.pf1", "nove7/nov25_55-10__ver4-try_time_1434.pf1",
				"nove7/nov25_60-10__ver4-try_time_13375.pf1", "nove7/nov25_65-10__ver4-try_time_115087.pf1",
				"nove7/nov25_70-10__ver4-try_time_11933.pf1", "nove7/nov25_75-10__ver4-try_time_2457.pf1",
				"nove7/nov25_80-10__ver4-try_time_5146.pf1", "nove7/nov25_85-10__ver4-try_time_3612.pf1",
				"nove7/nov25_90-10__ver4-try_time_12079.pf1", "nove7/nov25_95-10__ver4-try_time_75968.pf1",

		};
		String[] filename2 = new String[] {

				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem0505.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem1005.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem1505.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem2005.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem2505.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem3005.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem3505.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem4005.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem4505.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem5005.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem5505.rf",
				"nove7/NOV25-total-comparison-small4-2000iter/referenceFronts/FogPlanningProblem6005.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem3010.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem3510.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem4010.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem4510.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem5010.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem5510.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem6010.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem6510.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem7010.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem7510.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem8010.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem8510.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem9010.rf",
				"nove7/NOV26-total-comparison-big4-10000iter/referenceFronts/FogPlanningProblem9510.rf",

		};
		if (args.length < 2) {
			for (int i = 0; i < filename.length; i++) {
				InvertedGenerationalDistance qualityIndicator = new InvertedGenerationalDistance();

				// STEP 2. Read the fronts from the files
				double[][] solutionFront = qualityIndicator.utils_.readFront(filename[i]);
				double[][] trueFront = qualityIndicator.utils_.readFront(filename2[i]);
				double value = qualityIndicator.invertedGenerationalDistance(solutionFront, trueFront, 2);

				System.out.println(value);
			}
			System.exit(1);
		} // if
		for (int i = 0; i < 14; i++) {
			// STEP 1. Create an instance of Generational Distance
			InvertedGenerationalDistance qualityIndicator = new InvertedGenerationalDistance();

			// STEP 2. Read the fronts from the files
			double[][] solutionFront = qualityIndicator.utils_.readFront(args[0]);
			double[][] trueFront = qualityIndicator.utils_.readFront(args[1]);

			// STEP 3. Obtain the metric value
			double value = qualityIndicator.invertedGenerationalDistance(solutionFront, trueFront,
					new Integer(args[2]));

			System.out.println(value);
		} // main

	}
}// InvertedGenerationalDistance
