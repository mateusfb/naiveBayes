package naiveBayes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import util.Mathematics;

/** Classe que implementa o classificador NaiveBayes **/
public class NaiveBayes {
	
	private HashMap<String, Double> classOccurances; //> Armazena o numero de ocorrencias de cada classe no dataset
	private String[] classes; //> Armazena todas as possiveis classes do dataset
	
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
		
		// Iterando sobre as instancias do dataset, contando as ocorrencias de cada classe
		for(int i = 0; i < dataset.getInstances().size(); i++) {
			if(classOccurances.containsKey(dataset.getInstances().get(i).getLabel())) {
				classOccurances.put(dataset.getInstances().get(i).getLabel(), classOccurances.get(dataset.getInstances().get(i).getLabel()) + 1);
			}else {
				classOccurances.put(dataset.getInstances().get(i).getLabel(), 1.0);
			}
		}
		
		this.classes = classOccurances.keySet().toArray(classes); // Armazenando todas as classes distintas
	}
	
	/**
	 * Separa as instancias de acordo com suas classes
	 * @param dataset Dataset - Dataset a ser operado
	 * @return ArrayList<Dataset> - Lista de tabelas contendo as instancias pertencentes a cada classe
	 */
	public ArrayList<Dataset> separateByClass(Dataset dataset){
		ArrayList<Dataset> separated = new ArrayList<Dataset>(); //> Armazena os datasets relacionados a cada classe
		
		// Iterando sobre as classes do dataset (escolhendo uma classe C por vez para sem comparada)
		for(int i = 0; i < dataset.getNumClass(); i++) { 
			ArrayList<ObjectInstance> temp = new ArrayList<ObjectInstance>(); //> Temporariamente armazena as instancias de C
			Dataset classDataset = new Dataset();
			// Iterando sobre as instancias do dataset, armazenando em temp as que tem o rotulo C
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
		if(classDataset.getAttributeOccurances().get(column).containsKey(attribute)) { // Checando se a coluna de posicao column no dataset possui o atributo
			return classDataset.getAttributeOccurances().get(column).get(attribute) / classDataset.getInstances().size(); // Se possui o atributo, calcula sua probabilidade
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
		
		// Iterando sobre as classes do dataset (escolhendo uma classe C a ser comparada)
		for(int i = 0; i < dataset.getNumClass(); i++) {
			// Iterando sobre os atributos(colunas) do dataset, calculando as probabilidades para cada atributo da instancia de teste, dada a classe C
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

		// Iterando sobre o vetor de probabilidades e exibindo os resultados
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
		Dataset trainSet = new Dataset();
		trainSet.setInstances(trainInstances);
		trainSet.setNumAttributes(dataset.getNumAttributes());
		trainSet.setNumClass(dataset.getNumClass());
		
		double correct = 0;
		double incorrect = 0;
		
		ArrayList<Dataset> separated = separateByClass(trainSet);
		
		// Iterando sobre as instancias de teste, classificando e checando se acertou
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
