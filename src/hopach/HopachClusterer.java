package hopach;

import java.io.DataInput;
import java.io.RandomAccessFile;

import clusterMaker.algorithms.attributeClusterers.pam.*;
import clusterMaker.algorithms.attributeClusterers.hopach.*;
import clusterMaker.algorithms.attributeClusterers.hopach.types.SplitCost;
import clusterMaker.algorithms.attributeClusterers.BaseMatrix;
import clusterMaker.algorithms.attributeClusterers.Clusters;
import clusterMaker.algorithms.attributeClusterers.DistanceMatrix;
import clusterMaker.algorithms.attributeClusterers.DistanceMetric;
import clusterMaker.algorithms.numeric.MeanSummarizer;
import clusterMaker.algorithms.numeric.MedianSummarizer;
import clusterMaker.algorithms.numeric.PrimitiveMeanSummarizer;
import clusterMaker.algorithms.numeric.PrimitiveMedianSummarizer;
import clusterMaker.algorithms.numeric.PrimitiveSummarizer;
import clusterMaker.algorithms.numeric.Summarizer;

public class HopachClusterer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.out.println("Usage: hopach <data matrix file> [distance matrix file]");
		}
		
		String dataMatrixPath = args[0], distanceMatrixPath = args[1];
		
		// parameters
		DistanceMetric metric = DistanceMetric.EUCLIDEAN2;
		Summarizer summarizer = new MedianSummarizer();
		PrimitiveSummarizer psummarizer = new PrimitiveMedianSummarizer();
		int K = 9, L = 9, maxLevel = 15;
		double minCostReduction = 0;
		boolean forceInitSplit = true;
		SplitCost splitCost = SplitCost.AVERAGE_SPLIT_SILHOUETTE;
		int k = 8;
		
		// read data from file
		BaseMatrix data = new BaseMatrix(readBaseMatrix(dataMatrixPath));
		
		// read distance matrix from file
		Double[][] matrix = readBaseMatrix(distanceMatrixPath);
		DistanceMatrix distances = new DistanceMatrix(DoubleMatrixToPrimitive(matrix), metric, null);
	
		HopachablePAM partitioner = new HopachablePAM(data, metric, distances, null);
		partitioner.setParameters(K, L, splitCost, summarizer);
		
		HopachPAM hopachPam = new HopachPAM(partitioner);
		
		// generate best split
		System.out.println("Hopach-PAM");
		hopachPam.setParameters(maxLevel,  minCostReduction,  forceInitSplit, psummarizer);
		Clusters c = hopachPam.run();
		for (int i = 0; i < c.size(); ++i) {
			System.out.println(c.getLabel(i));
		}
		
		// generate top level split
		System.out.println("Hopach-PAM, top level");
		hopachPam.setParameters(1, minCostReduction, forceInitSplit, psummarizer);
		c = hopachPam.run();
		for (int i = 0; i < c.size(); ++i) {
			System.out.println(c.getLabel(i));
		}
		
		System.out.println("PAM, k = " + k);
		PAM pam = new PAM(data, metric, distances, null);
		c = pam.cluster(k);
		for (int i = 0; i < c.size(); ++i) {
			System.out.println(c.getLabel(i));
		}
		
		
		/*
		for (int i = 0; i < data.nRows(); ++i) {
			for (int j = 0; j < data.nColumns(); ++j) {
				System.out.print(data.getValue(i, j) + ", ");
			}
			System.out.println("");
		}
		*/

	}
	
	private static double[][] DoubleMatrixToPrimitive(Double[][] matrix) {
		// construct new array with primitive doubles
		double[][] matrix2 = new double[matrix.length][];
		for (int i = 0; i < matrix.length; ++i) {
			matrix2[i] = new double[matrix[0].length];
			for (int j = 0; j < matrix[0].length; ++j) {
				matrix2[i][j] = matrix[i][j];
			}
		}
		return matrix2;
	}
	
	private static int[] getDimensions(DataInput f) {
		int m = 0, n = 0;
		try {
			String line;
			while ((line = f.readLine()) != null) {
				String[] tokens = line.split(delimiter);
				if (n == 0) {
					n = tokens.length;
				} else if (n != tokens.length) {
					throw new Exception("Line " + m + " does not have " + n + " columns");
				}
				++m;
			} 
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return new int[] {m, n}; 
	}
	
	private static Double[][] readBaseMatrix(String fname) {
		Double[][] bm = null;
		try {
			
			RandomAccessFile f = new RandomAccessFile(fname, "r");
			
			int[] dims = getDimensions(f);
			bm = new Double[dims[0]][dims[1]];
			f.seek(0);
			
			String line;
			int i = 0;
			while ((line = f.readLine()) != null) {
				String[] tokens = line.split(delimiter);
				for (int j = 0; j < tokens.length; ++j) {
					bm[i][j] = Double.valueOf(tokens[j]);
				}
				++i;
			} 
		
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			
		}
		return bm;
	}
	
	final static String delimiter = "\t";

}
