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

		return Math.sqrt(sum / values.length);
	}
	
	public static double gaussianPdf(double attribute, double mean, double stdev) {
		return (1/Math.sqrt(2*Math.PI)*stdev)*Math.exp(-Math.pow((attribute-mean), 2)/2*Math.pow(stdev, 2));
	}
	
	public static double multiplyArray(double[] values) {
		double result = 1;
		for(double value : values) {
			result = result * value;
		}
		
		return result;
	}
}
