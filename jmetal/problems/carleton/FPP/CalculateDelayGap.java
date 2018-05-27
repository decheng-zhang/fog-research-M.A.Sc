package jmetal.problems.carleton.FPP;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CalculateDelayGap {

	// a is emo, b is cplex result
	public static double readFile(String a, String b) {
		ArrayList<Double> a1 = new ArrayList<Double>();
		ArrayList<Double> a2 = new ArrayList<Double>();
		ArrayList<Double> b1 = new ArrayList<Double>();
		ArrayList<Double> b2 = new ArrayList<Double>();
		readin(a, a1, a2);
		readin(b, b1, b2);

		return Calculategap(a1, a2, b1, b2);

	}

	public static void readin(String path, List<Double> x, List<Double> y) {
		try {
			/* Open the file */
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			String aux = br.readLine();
			while (aux != null) {
				StringTokenizer st = new StringTokenizer(aux);
				x.add(new Double(st.nextToken()));
				y.add(new Double(st.nextToken()));
				aux = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			System.out.println("jmetal.qualityIndicator.util.readNonDominatedSolutionSet: " + path);
			e.printStackTrace();

		}
	}

	public static double Calculategap(List<Double> a1, List<Double> a2, List<Double> b1, List<Double> b2) {
		double gap = 0.0;
		int cnt = 0;
		for (double b : b1) {

			int emo = greaterEqual(a1, b);
			if (emo == 0 || emo == a1.size()) {
				cnt++;
				continue;
			}
			double delayemo = a2.get(emo);
			gap += delayemo / b2.get(cnt++) - 1;
			// System.out.println((delayemo)+"v-----"+b2.get(cnt-1));
		}
		return gap / cnt;
	}

	private static int greaterEqual(List<Double> pricetags, double target) {
		int low = 0, high = pricetags.size();

		while (low < high) {
			int mid = low + ((high - low) >> 1);
			if (pricetags.get(mid) < target) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}
		return low;
	}

	// TODO Auto-generated constructor stub

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] b = new String[] {
				/*
				 * "nove4/big1cplex/nov25_30-10__ver1-try_time_5549.pf1",
				 * "nove4/big1cplex/nov25_35-10__ver1-try_time_2068.pf1",
				 * "nove4/big1cplex/nov25_40-10__ver1-try_time_12215.pf1",
				 * "nove4/big1cplex/nov25_45-10__ver1-try_time_1896.pf1",
				 * "nove4/big1cplex/nov25_50-10__ver1-try_time_2447.pf1",
				 * "nove4/big1cplex/nov25_55-10__ver1-try_time_5330.pf1",
				 * "nove4/big1cplex/nov25_60-10__ver1-try_time_11056.pf1",
				 * "nove4/big1cplex/nov25_65-10__ver1-try_time_2482559.pf1",
				 * "nove4/big1cplex/nov25_70-10__ver1-try_time_1601.pf1",
				 * "nove4/big1cplex/nov26-bigtwothree_75-10__ver1-try_time_3701882.pf1",
				 * "nove4/big1cplex/nov26-bigtwothree_80-10__ver1-try_time_5977.pf1",
				 * "nove4/big1cplex/nov26-bigtwothree_85-10__ver1-try_time_71582.pf1",
				 * "nove4/big1cplex/nov26-bigtwothree_90-10__ver1-try_time_16059.pf1",
				 * "nove4/big1cplex/nov26-bigtwothree_95-10__ver1-try_time_4978.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_5-5__versmall-try_time_498.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_10-5__versmall-try_time_438.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_15-5__versmall-try_time_240.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_20-5__versmall-try_time_807.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_25-5__versmall-try_time_839.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_30-5__versmall-try_time_1214.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_35-5__versmall-try_time_703.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_40-5__versmall-try_time_1001.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_45-5__versmall-try_time_868.pf1",
				 * 
				 * "nove4/small1cplex/nov26-smallonefour_50-5__versmall-try_time_709.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_55-5__versmall-try_time_11263.pf1",
				 * "nove4/small1cplex/nov26-smallonefour_60-5__versmall-try_time_1696.pf1",
				 *//*
					 * "nove5/nov25-smalltwothree_5-5__versmall2-try_time_323.pf1",
					 * "nove5/nov25-smalltwothree_10-5__versmall2-try_time_457.pf1",
					 * "nove5/nov25-smalltwothree_15-5__versmall2-try_time_410.pf1",
					 * "nove5/nov25-smalltwothree_20-5__versmall2-try_time_380.pf1",
					 * "nove5/nov25-smalltwothree_25-5__versmall2-try_time_812.pf1",
					 * "nove5/nov25-smalltwothree_30-5__versmall2-try_time_850.pf1",
					 * "nove5/nov25-smalltwothree_35-5__versmall2-try_time_845.pf1",
					 * "nove5/nov25-smalltwothree_40-5__versmall2-try_time_779.pf1",
					 * "nove5/nov25-smalltwothree_45-5__versmall2-try_time_34681.pf1",
					 * 
					 * "nove5/nov25-smalltwothree_50-5__versmall2-try_time_999.pf1",
					 * "nove5/nov25-smalltwothree_55-5__versmall2-try_time_7724.pf1",
					 * "nove5/nov25-smalltwothree_60-5__versmall2-try_time_8620.pf1",
					 * "nove5/nov25-bigtwothree_30-10__ver2-try_time_4004.pf1",
					 * "nove5/nov25-bigtwothree_35-10__ver2-try_time_8997.pf1",
					 * "nove5/nov25-bigtwothree_40-10__ver2-try_time_1959.pf1",
					 * "nove5/nov25-bigtwothree_45-10__ver2-try_time_4831.pf1",
					 * "nove5/nov25-bigtwothree_50-10__ver2-try_time_12027.pf1",
					 * "nove5/nov25-bigtwothree_55-10__ver2-try_time_3785.pf1",
					 * "nove5/nov25-bigtwothree_60-10__ver2-try_time_18618.pf1",
					 * "nove5/nov25-bigtwothree_65-10__ver2-try_time_4339.pf1",
					 * "nove5/nov25-bigtwothree_70-10__ver2-try_time_6549.pf1",
					 * "nove5/nov25-bigtwothree_75-10__ver2-try_time_3166.pf1",
					 * "nove5/nov25-bigtwothree_80-10__ver2-try_time_2563.pf1",
					 * "nove5/nov25-bigtwothree_85-10__ver2-try_time_1688.pf1",
					 * "nove5/nov25-bigtwothree_90-10__ver2-try_time_5328.pf1",
					 * "nove5/nov25-bigtwothree_95-10__ver2-try_time_12859.pf1",
					 */
				/*
				 * "nove6/nov25-bigtwothree_30-10__ver3-try_time_3639.pf1",
				 * "nove6/nov25-bigtwothree_35-10__ver3-try_time_32961.pf1",
				 * "nove6/nov25-bigtwothree_40-10__ver3-try_time_3158.pf1",
				 * "nove6/nov25-bigtwothree_45-10__ver3-try_time_4906.pf1",
				 * "nove6/nov25-bigtwothree_50-10__ver3-try_time_12472.pf1",
				 * "nove6/nov25-bigtwothree_55-10__ver3-try_time_20468.pf1",
				 * "nove6/nov25-bigtwothree_60-10__ver3-try_time_5468.pf1",
				 * "nove6/nov25-bigtwothree_65-10__ver3-try_time_35050.pf1",
				 * "nove6/nov25-bigtwothree_70-10__ver3-try_time_3648.pf1",
				 * "nove6/nov25-bigtwothree_75-10__ver3-try_time_3820.pf1",
				 * "nove6/nov25-smalltwothree_5-5__versmall3-try_time_436.pf1",
				 * "nove6/nov25-smalltwothree_10-5__versmall3-try_time_375.pf1",
				 * "nove6/nov25-smalltwothree_15-5__versmall3-try_time_574.pf1",
				 * "nove6/nov25-smalltwothree_20-5__versmall3-try_time_15924.pf1",
				 * "nove6/nov25-smalltwothree_25-5__versmall3-try_time_546.pf1",
				 * "nove6/nov25-smalltwothree_30-5__versmall3-try_time_758.pf1",
				 * "nove6/nov25-smalltwothree_35-5__versmall3-try_time_1827.pf1",
				 * "nove6/nov25-smalltwothree_40-5__versmall3-try_time_1389.pf1",
				 * "nove6/nov25-smalltwothree_45-5__versmall3-try_time_1212.pf1",
				 * 
				 * "nove6/nov25-smalltwothree_50-5__versmall3-try_time_1458.pf1",
				 * "nove6/nov25-smalltwothree_55-5__versmall3-try_time_1338.pf1",
				 * "nove6/nov25-smalltwothree_60-5__versmall3-try_time_8647.pf1",
				 * "nove6/nov26-bigtwothree_80-10__ver3-try_time_3737699.pf1",
				 * "nove6/nov26-bigtwothree_85-10__ver3-try_time_205082.pf1",
				 * "nove6/nov26-bigtwothree_90-10__ver3-try_time_61684.pf1",
				 * "nove6/nov26-bigtwothree_95-10__ver3-try_time_136941.pf1",
				 * 
				 */
				"nove7/nov25_30-10__ver4-try_time_7242782.pf1", "nove7/nov25_35-10__ver4-try_time_3958.pf1",
				"nove7/nov25_40-10__ver4-try_time_37319.pf1", "nove7/nov25_45-10__ver4-try_time_2909.pf1",
				"nove7/nov25_50-10__ver4-try_time_8887.pf1", "nove7/nov25_55-10__ver4-try_time_1434.pf1",
				"nove7/nov25_60-10__ver4-try_time_13375.pf1", "nove7/nov25_65-10__ver4-try_time_115087.pf1",
				"nove7/nov25_70-10__ver4-try_time_11933.pf1", "nove7/nov25_75-10__ver4-try_time_2457.pf1",
				"nove7/nov25_80-10__ver4-try_time_5146.pf1", "nove7/nov25_85-10__ver4-try_time_3612.pf1",
				"nove7/nov25_90-10__ver4-try_time_12079.pf1", "nove7/nov25_95-10__ver4-try_time_75968.pf1",
				"nove7/nov26-smallonefour_5-5__versmall4-try_time_459.pf1",
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

				/*
				 * "NSGAII/FogPlanningProblem10010/test.t",
				 * "NSGAII/FogPlanningProblem20010/test.t",
				 * "NSGAII/FogPlanningProblem30010/test.t",
				 * "NSGAII/FogPlanningProblem40010/test.t",
				 * "NSGAII/FogPlanningProblem50010/test.t", "nove2/30-10.pf", "nove2/35-10.pf",
				 * "nove2/40-10.pf", "nove2/45-10.pf", "nove2/50-10.pf", "nove2/55-10.pf",
				 * "nove2/60-10.pf", "nove2/65-10.pf", "nove2/70-10.pf", "nove2/75-10.pf",
				 * "nove2/80-10.pf", "nove2/85-10.pf", "nove2/90-10.pf","nove2/95-10.pf"
				 * "nove3/5-5.pf", "nove3/10-5.pf", "nove3/15-5.pf", "nove3/20-5.pf",
				 * "nove3/25-5.pf", "nove3/30-5.pf", "nove3/35-5.pf", "nove3/40-5.pf",
				 * "nove3/45-5.pf", "nove3/50-5.pf", "nove3/55-5.pf", "nove3/60-5.pf",
				 * 
				 */
		};
		String[] a = new String[] { /*
									 * small2 refercefile
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem0505.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem1005.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem1505.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem2005.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem2505.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem3005.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem3505.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem4005.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem4505.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem5005.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem5505.rf",
									 * "nove3/NOV25-total-comparison-small2-2000iter//referenceFronts/FogPlanningProblem6005.rf",
									 *//*
										 * small3 ref
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem0505/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem1005/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem1505/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem2005/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem2505/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem3005/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem3505/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem4005/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem4505/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem5005/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem5505/UnionFront",
										 * "nove3/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem6005/UnionFront",
										 */
				/*
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem3010/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem3510/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem4010/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem4510/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem5010/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem5510/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem6010/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem6510/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem7010/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem7510/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem8010/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem8510/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem9010/UnionFront",
				 * "nove4/NOV26-total-comparison-bigver1-10000iter/data/SMPSO/FogPlanningProblem9510/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem0505/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem1005/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem1505/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem2005/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem2505/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem3005/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem3505/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem4005/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem4505/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem5005/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem5505/UnionFront",
				 * "nove4/NOV26-total-comparison-small1-2000iter/data/SMPSO/FogPlanningProblem6005/UnionFront",
				 *//*
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem0505/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem1005/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem1505/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem2005/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem2505/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem3005/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem3505/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem4005/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem4505/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem5005/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem5505/UnionFront",
					 * "nove5/NOV25-total-comparison-small2-2000iter/data/PSONSGAII/FogPlanningProblem6005/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem3010/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem3510/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem4010/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem4510/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem5010/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem5510/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem6010/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem6510/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem7010/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem7510/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem8010/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem8510/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem9010/UnionFront",
					 * "nove5/NOV26-total-comparison-big2-10000iter/data/PSONSGAII/FogPlanningProblem9510/UnionFront",
					 * 
					 */
				/*
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem3010/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem3510/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem4010/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem4510/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem5010/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem5510/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem6010/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem6510/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem7010/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem7510/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem8010/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem8510/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem9010/UnionFront",
				 * "nove6/NOV26-total-comparison-big3-10000iter/data/PSONSGAII/FogPlanningProblem9510/UnionFront",
				 * 
				 * 
				 * 
				 * 
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem0505/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem1005/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem1505/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem2005/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem2505/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem3005/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem3505/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem4005/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem4505/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem5005/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem5505/UnionFront",
				 * "nove6/NOV25-total-comparison-small3-2000iter/data/PSONSGAII/FogPlanningProblem6005/UnionFront",
				 * 
				 */
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem3010/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem3510/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem4010/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem4510/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem5010/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem5510/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem6010/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem6510/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem7010/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem7510/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem8010/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem8510/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem9010/UnionFront",
				"nove7/NOV26-total-comparison-big4-10000iter/data/PSONSGAII/FogPlanningProblem9510/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem0505/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem1005/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem1505/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem2005/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem2505/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem3005/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem3505/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem4005/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem4505/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem5005/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem5505/UnionFront",
				"nove7/NOV25-total-comparison-small4-2000iter/data/PSONSGAII/FogPlanningProblem6005/UnionFront",

		};
		for (int i = 0; i < a.length; i++) {
			System.out.println(readFile(a[i], b[i]));
		}

	}

}
