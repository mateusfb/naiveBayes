package util;

import java.util.ArrayList;

public class Statistics {
	
	public static double mean(double[] values) {
		double sum = Statistics.sumArray(values);
		
		return sum/values.length;
	}
	
	public static double mean(ArrayList<Double> values) {
		double sum = Statistics.sumArrayList(values);
		
		return sum/values.size();
	}
	
	public static double stdev(double[] values) {
		double mean = Statistics.mean(values);
		double sum = 0;
		
		for(int i = 0; i < values.length; i++) {
			sum += Math.pow(values[i] - mean, 2);
		}

		return Math.sqrt(sum / values.length);
	}
	
	public static double stdev(ArrayList<Double> values) {
		double mean = Statistics.mean(values);
		double sum = 0;
		
		for(Double value : values) {
			sum += Math.pow(value - mean, 2) / values.size();
		}
		
		return Math.sqrt(sum);
	}
	
	public static double gaussianPdf(double attribute, double mean, double stdev) {
		double exp = Math.exp(-(Math.pow((attribute-mean), 2)/2*Math.pow(stdev, 2)));
		return (1/Math.sqrt(2*Math.PI*stdev))*exp;
	}
	
	public static double sumArray(double[] values) {
		double sum = 0;
		
		for(double value : values) {
			sum += value;
		}
		
		return sum;
	}
	
	public static double sumArrayList(ArrayList<Double> values) {
		double sum = 0;
		
		for(Double value : values) {
			sum += value;
		}
		
		return sum;
	}
	
	public static double multiplyArray(double[] values) {
		double result = 1;
		
		for(double value : values) {
			result = result * value;
		}
		
		return result;
	}
	
	public static double multiplyArrayList(ArrayList<Double> values) {
		double result = 1;
		
		for(double value : values) {
			result = result * value;
		}
		
		return result;
	}
	
	public static double findMax(double[] values) {
		double max = 0;
		
		for(double value : values) {
			max = Math.max(max, value);
		}
		
		return max;
	}
	
	public static int findMaxIdx(double[] values) {
		double max = 0;
		int maxIdx = 0;
		
		for(int i = 0; i < values.length; i++) {
			if(max < values[i]) {
				max = values[i];
				maxIdx = i;
			}
		}
		
		return maxIdx;
	}
	
	public static int findMaxIdx(ArrayList<Double> values) {
		double max = 0;
		int maxIdx = 0;
		
		for(int i = 0; i < values.size(); i++) {
			if(max < values.get(i)) {
				max = values.get(i);
				maxIdx = i;
			}
		}
		
		return maxIdx;
	}
}
