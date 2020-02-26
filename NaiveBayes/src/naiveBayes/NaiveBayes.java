package naiveBayes;

import java.util.ArrayList;
import java.util.HashMap;

import util.Statistics;

public class NaiveBayes {
	
	private HashMap<String, Integer> classOccurances;
	private String[] classes;
	
	public void buildClassifier(Dataset dataset) {
		countClassOccurances(dataset);
	}
	
	public void countClassOccurances(Dataset dataset){
		this.classes = new String[dataset.getNumClass()];
		classOccurances = new HashMap<>();
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
		
		for(int i = 0; i < dataset.getNumClass(); i++) { 
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
	
	public double probabilityOfNominalAttribute(ArrayList<ObjectInstance> classInstances, int column, String attribute) {
		double count = 0;
		
		for(ObjectInstance instance: classInstances) {
			if(instance.getAttributes().get(column).equals(attribute)){
				count++;
			}
		}
		
		return count / (double) classInstances.size();
	}
	
	public void test(Dataset dataset, String[] tested) {
		double[] classProbabilities = new double[dataset.getNumClass()];
		double[] attributeProbabilities = new double[dataset.getNumAttributes()];
		double[] statistics;
		ArrayList<ArrayList<ObjectInstance>> separated = separateByClass(dataset);
		
		for(int i = 0; i < dataset.getNumClass(); i++) { //i representa as classes
			for(int j = 0; j < dataset.getNumAttributes(); j++) { //j representa os atributos
				if(dataset.getDataTypes()[j] == 0) {
					statistics = statisticsByColumn(separated.get(i), j, classes[i]);
					attributeProbabilities[j] = Statistics.gaussianPdf(Double.parseDouble(tested[j]), statistics[0], statistics[1]);
				} else {
					attributeProbabilities[j] = probabilityOfNominalAttribute(separated.get(i), j, tested[j]);
				}
			}
			classProbabilities[i] = Statistics.multiplyArray(attributeProbabilities) * ((double)classOccurances.get(classes[i]) / dataset.getInstances().size());
		}
		
		printResult(classProbabilities);
	}
	
	public void printResult(double[] classProbabilities) {
		double sum = Statistics.sumArray(classProbabilities);
		
		for(int i = 0; i < classes.length; i++) {
			System.out.println(classes[i] + ": " + (classProbabilities[i] / sum)*100 + "%");
		}
	}
}
