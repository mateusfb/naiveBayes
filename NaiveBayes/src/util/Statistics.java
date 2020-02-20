package util;

public class Statistics {
	
	public static double mean(double[] values) {
		double sum = 0;
		
		for(double value : values) {
			sum += value;
		}
		
		return sum/values.length;
	}
	
	public static double stdev(double[] values) {
		double mean = Statistics.mean(values);
		double sum = 0;
		
		for(double value: values) {
			sum += Math.pow(value - mean, 2);
		}
		
		return Math.sqrt(sum / values.length - 1);
	}
}
