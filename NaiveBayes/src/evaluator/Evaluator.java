package evaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import data.Dataset;
import data.ObjectInstance;
import naiveBayes.Classifier;

public class Evaluator {
	
	public Dataset[] split(Dataset dataset, int percentage) {
		ArrayList<ObjectInstance> shuffledDataset = dataset.getInstances();
		Collections.shuffle(shuffledDataset, new Random(1)); // Embaralhando o vetor de instancias
		
		int trainSize = (percentage*shuffledDataset.size())/100; // Definindo o tamanho do dataset de treino (percentage% do dataset original)
		
		ArrayList<ObjectInstance> trainInstances = new ArrayList<ObjectInstance>(shuffledDataset.subList(0, trainSize)); // Definindo as instancias de treino
		ArrayList<ObjectInstance> testInstances = new ArrayList<ObjectInstance>(shuffledDataset.subList(trainSize, shuffledDataset.size())); //Definindo as instancias de teste
		
		// Criando o dataset de treino
		Dataset trainSet = new Dataset();
		trainSet.setInstances(trainInstances);
		trainSet.setNumAttributes(dataset.getNumAttributes());
		trainSet.setNumClass(dataset.getNumClass());
		
		//Criando o dataset de teste
		Dataset testSet = new Dataset();
		testSet.setInstances(testInstances);
		testSet.setNumAttributes(dataset.getNumAttributes());
		trainSet.setNumClass(dataset.getNumClass());
		
		return new Dataset[] {trainSet, testSet};
	}
	
	public void splitTest(Dataset dataset, double splitPercentage, Classifier classifier) {
		
		Dataset[] temp = split(dataset, 66);
		Dataset trainSet = temp[0];
		Dataset testSet = temp[1];
				
		double correct = 0;
		double incorrect = 0;
		
		ArrayList<Dataset> separated = classifier.separateByClass(trainSet);
		
		// Iterando sobre as instancias de teste, classificando e checando se acertou
		for(ObjectInstance instance : testSet.getInstances()) {
			String[] values = new String[trainSet.getNumAttributes()];
			values = instance.getValues().toArray(values);
			
			if(instance.getLabel().equals(classifier.predictInstanceClass(dataset, instance.getValues().toArray(new String[dataset.getNumAttributes()]), separated))) {
				correct++;
			} else {
				incorrect++;
			}
		}
		
		printResult(correct, incorrect, testSet.getInstances().size());
	}
	
	public void printResult(double correct, double incorrect, int testSize) {
		// Imprimindo os resultados
		System.out.println("correct: " + correct / testSize * 100 + "%" + " (" + correct + ")");
		System.out.println("incorrect: " + incorrect / testSize * 100 + "%" + " (" + incorrect + ")");
	}
}
