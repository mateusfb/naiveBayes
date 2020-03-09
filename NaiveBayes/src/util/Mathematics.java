package util;

import java.util.ArrayList;

public class Mathematics {
	
	public static double sumArrayList(ArrayList<Double> values) {
		double sum = 0;
		
		for(Double value : values) {
			sum += value;
		}
		
		return sum;
	}
	
	public static double multiplyArrayList(ArrayList<Double> values) {
		double result = 1;
		
		for(double value : values) {
			result = result * value;
		}
		
		return result;
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
