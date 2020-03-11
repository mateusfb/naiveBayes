package naiveBayes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import util.Mathematics;

/** Classe que implementa o classificador NaiveBayes **/
public class NaiveBayes {
	
	private HashMap<String, Integer> classOccurances; //> Armazena o numero de ocorrencias de cada classe no dataset
	private String[] classes; //> Armazena todas as classes
	
	/** 
	 * Construtor da classe 
	 * @param dataset Dataset - Dataset a ser operado
	 **/
	public NaiveBayes(Dataset dataset) {
		countClassOccurances(dataset);
	}
	
	/**
	 * Conta o numero de ocorrencias de cada classe no dataset
	 * @param dataset Dataset - Dataset a ser operado
	 */
	public void countClassOccurances(Dataset dataset){
		this.classes = new String[dataset.getNumClass()];
		classOccurances = new HashMap<>();
		int count = 0; //> Contador do numero de classes distintas
		
		// Contando as ocorrencias e registrando as classes
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
	
	/**
	 * Separa as instancias de acordo com as classes as quais pertencem
	 * @param dataset Dataset - Dataset a ser operado
	 * @return ArrayList<Dataset> - tabela contendo todos as instancias associadas as suas respectivas classes
	 */
	public ArrayList<Dataset> separateByClass(Dataset dataset){
		ArrayList<Dataset> separated = new ArrayList<Dataset>(); //> Armazena os datasets relacionados a cada classe
		
		// Definindo a classe C a ser comparada
		for(int i = 0; i < dataset.getNumClass(); i++) { 
			ArrayList<ObjectInstance> temp = new ArrayList<ObjectInstance>(); //> Temporariamente armazena as instancias de C
			Dataset classDataset = new Dataset("null");
			// Percorrendo todo o dataset, armazenando em temp as instancias de C
			for(ObjectInstance instance : dataset.getInstances()) {
				if(instance.getLabel().equals(classes[i])) {
					temp.add(instance);
				}
			}
			
			// Definindo o dataset relacionado a classe C
			classDataset.setInstances(temp);
			classDataset.setNumAttributes(dataset.getNumAttributes());
			classDataset.setNumClass(1);
			classDataset.countAttributeOccurances(); // Contando e armazenando as ocorrencias dos atributos
			
			separated.add(classDataset);
		}
		return separated;
	}
	
	/**
	 * Calcula a probabilidade de ocorrencia de um atributo nominal para uma determinada classe
	 * @param classInstances Dataset - Dataset relacionado a classe
	 * @param column int - Posicao do atributo na lista
	 * @param attribute String - Valor do atributo
	 * @return double - Probabilidade de ocorrencia do atributo
	 */
	public double probabilityOfNominalAttribute(Dataset classDataset, int column, String attribute) {
		if(classDataset.getAttributeOccurances().get(column).containsKey(attribute)) {
			return classDataset.getAttributeOccurances().get(column).get(attribute) / classDataset.getInstances().size();
		}
		return 0;
	}
	
	/**
	 * Calcula as probabilidades de uma nova instancia pertencer as classes do dataset
	 * @param dataset Dataset - Dataset a ser operado
	 * @param tested String[] - Vetor contendo os valores de atributo da nova instancia
	 * @param separated ArrayList<Dataset> - Lista de datasets reacionados a cada classe
	 * @return ArrayList<Double> - Lista de probabilidades para as classes
	 */
	public ArrayList<Double> calculateClassProbabilities(Dataset dataset, String[] tested, ArrayList<Dataset> separated) {
		ArrayList<Double> classProbabilities = new ArrayList<Double>(); //> Armazena as probabilidades da nova instancia pertencer as classes do dataset
		ArrayList<Double> attributeProbabilities = new ArrayList<Double>(); //> Armazena temporariamente as probabilidades para cada atributo da instancia, dadas as classes
		
		// Definindo a classe C a ser comparada
		for(int i = 0; i < dataset.getNumClass(); i++) {
			// Calculando as probabilidades para cada atributo da instancia, dada a classe C
			for(int j = 0; j < dataset.getNumAttributes(); j++) {
				attributeProbabilities.add(probabilityOfNominalAttribute(separated.get(i), j, tested[j]));
			}
			classProbabilities.add(Mathematics.multiplyArrayList(attributeProbabilities) * ((double)classOccurances.get(classes[i]) / dataset.getInstances().size())); // Calculando a estimativa da instancia pertencer a classe C
			attributeProbabilities.clear();
		}
		
		return classProbabilities;
	}
	
	
	/**
	 * Classifica e exibe os resultados da classificacao de uma nova instancia
	 * @param dataset Dataset - dataset a ser operado
	 * @param tested - Vetor contendo os valores de atributo da nova instancia
	 */
	public void classify(Dataset dataset, String[] tested) {
		printResult(calculateClassProbabilities(dataset, tested, separateByClass(dataset)));
	}
	
	/**
	 * Exibe os resultados da classificação de uma nova instancia
	 * @param classProbabilities ArrayList<Double> - Lista contendo as probabilidades para cada classe
	 */
	public void printResult(ArrayList<Double> classProbabilities) {
		double sum = Mathematics.sumArrayList(classProbabilities);

		for(int i = 0; i < classes.length; i++) {
			System.out.println(classes[i] + ": " + (classProbabilities.get(i) / sum)*100 + "%");
		}
	}

	
	/**
	 * Testa a acuracia do classificador sobre um dataset, através de um split
	 * @param dataset Dataset - Dataset a ser operado
	 */
	public void splitTest(Dataset dataset) {
		ArrayList<ObjectInstance> scrambled = dataset.getInstances();
		Collections.shuffle(scrambled, new Random(1)); // Embaralhando o vetor de instâncias
		
		int trainSize = (66*scrambled.size())/100; // Definindo o tamanho do dataset de treino (66% do dataset original)
		
		ArrayList<ObjectInstance> trainInstances = new ArrayList<ObjectInstance>(scrambled.subList(0, trainSize)); // Definindo as instancias de treino
		ArrayList<ObjectInstance> testInstances = new ArrayList<ObjectInstance>(scrambled.subList(trainSize, scrambled.size())); //Definindo as instancias de teste
		
		// Criando o dataset de treino
		Dataset trainSet = new Dataset("null");
		trainSet.setInstances(trainInstances);
		trainSet.setNumAttributes(dataset.getNumAttributes());
		trainSet.setNumClass(dataset.getNumClass());
		
		double correct = 0;
		double incorrect = 0;
		
		ArrayList<Dataset> separated = separateByClass(trainSet);
		
		// Classificando cada instancia de teste e checando se acertou
		for(ObjectInstance instance : testInstances) {
			String[] attributes = new String[trainSet.getNumAttributes()];
			attributes = instance.getAttributes().toArray(attributes);
			ArrayList<Double> instanceProbabilities = calculateClassProbabilities(trainSet, attributes, separated);
			
			int maxIdx = Mathematics.findMaxIdx(instanceProbabilities);
			
			if(instance.getLabel().equals(classes[maxIdx])) {
				correct++;
			} else {
				incorrect++;
			}
		}
		
		// Imprimindo os resultados
		System.out.println("correct: " + correct / testInstances.size() * 100 + "%" + " (" + correct + ")");
		System.out.println("incorrect: " + incorrect / testInstances.size() * 100 + "%" + " (" + incorrect + ")");
	}
}
