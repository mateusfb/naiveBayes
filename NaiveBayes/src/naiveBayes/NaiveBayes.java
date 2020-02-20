package naiveBayes;

import java.util.ArrayList;
import java.util.HashMap;

import util.Statistics;

public class NaiveBayes {
	
	private HashMap<String, Integer> classOccurances;
	private String[] classes;
	
	public void buildClassifier(Dataset dataset) {
		
	}
	
	public void countClassOccurances(Dataset dataset){
		this.classes = new String[dataset.getNumClass()];
		int count = 0;
		
		for(int i = 0; i < dataset.getInstances().size(); i++) {
			if(classOccurances.containsKey(dataset.getInstances().get(i).getLabel())) {
				classOccurances.put(dataset.getInstances().get(i).getLabel(), classOccurances.get(dataset.getInstances().get(i).getLabel()) + 1);
			}else {
				classOccurances.put(dataset.getInstances().get(i).getLabel(), 1);
				classes[count] = dataset.getInstances().get(i).getLabel();
				count++;
			}
		}
	}
	
	public ArrayList<ArrayList<ObjectInstance>> separateByClass(Dataset dataset){
		ArrayList<ArrayList<ObjectInstance>> separated = new ArrayList<ArrayList<ObjectInstance>>();
		
		for(int i = 0; i < classes.length; i++) {
			ArrayList<ObjectInstance> temp = new ArrayList<ObjectInstance>();
			for(ObjectInstance instance : dataset.getInstances()) {
				if(instance.getLabel().equals(classes[i])) {
					temp.add(instance);
				}
			}
			separated.add(temp);
		}
		return separated;
	}

	public double[] statisticsByColumn(ArrayList<ObjectInstance> classInstances, int column, String label) {
		double[] columnData = new double[classOccurances.get(label)];
		double[] statistics = new double[2];
		
		for(int i = 0; i < classInstances.size(); i++) {
			columnData[i] = Double.parseDouble(classInstances.get(i).getAttributes().get(column));
		}
		
		statistics[0] = Statistics.mean(columnData);
		statistics[1] = Statistics.stdev(columnData);
		
		return statistics;
	}
}
