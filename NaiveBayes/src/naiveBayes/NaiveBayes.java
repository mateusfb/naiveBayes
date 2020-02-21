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
	
	public void test(Dataset dataset, double[] tested) {
		double[] classProbabilities = new double[dataset.getNumClass()];
		double[] attributeProbabilities = new double[dataset.getNumAttributes()];
		double[] statistics;
		ArrayList<ArrayList<ObjectInstance>> separated = separateByClass(dataset);
		
		for(int i = 0; i < dataset.getNumClass(); i++) { //i representa as classes
			for(int j = 0; j < dataset.getNumAttributes(); j++) { //j representa os atributos
				statistics = statisticsByColumn(separated.get(i), j, classes[i]);
				//System.out.println(statistics[0]);
				//System.out.println(statistics[1]);
				attributeProbabilities[j] = Statistics.gaussianPdf(tested[j], statistics[0], statistics[1]);
				//System.out.println(attributeProbabilities[j]);
			}
			
			classProbabilities[i] = Statistics.multiplyArray(attributeProbabilities) * ((double)classOccurances.get(classes[i]) / dataset.getInstances().size());
			//System.out.println(Statistics.multiplyArray(attributeProbabilities));
			//System.out.println(classProbabilities[i]);
			//System.out.println(classes[i]);
		}
		
		double sum = Statistics.sumArray(classProbabilities);
		
		//int maxIdx = Statistics.findMaxIdx(classProbabilities);
		System.out.println(classes[0] + ": " + (classProbabilities[0] / sum)*100);
		System.out.println(classes[1] + ": " + (classProbabilities[1] / sum)*100);
		System.out.println(classes[2] + ": " + (classProbabilities[2] / sum)*100);
	}
}
