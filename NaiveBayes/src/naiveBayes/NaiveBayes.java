package naiveBayes;

import java.util.ArrayList;
import java.util.HashMap;

import util.Statistics;

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
	 * @return ArrayList<ArrayList<ObjectInstance>> - tabela contendo todos as instancias associadas as suas respectivas classes
	 */
	public ArrayList<ArrayList<ObjectInstance>> separateByClass(Dataset dataset){
		ArrayList<ArrayList<ObjectInstance>> separated = new ArrayList<ArrayList<ObjectInstance>>(); //> Armazena as listas contendo as instancias relacionadas a cada classe
		
		// Definindo a classe C a ser comparada
		for(int i = 0; i < dataset.getNumClass(); i++) { 
			ArrayList<ObjectInstance> temp = new ArrayList<ObjectInstance>(); //> Temporariamente armazena as instancias de C
			// Percorrendo todo o dataset, armazenando em temp as instancias de C
			for(ObjectInstance instance : dataset.getInstances()) {
				if(instance.getLabel().equals(classes[i])) {
					temp.add(instance);
				}
			}
			separated.add(temp);
		}
		return separated;
	}
	
	/**
	 * Calcula media e desvio padrao de um determinado atributo do dataset
	 * @param classInstances ArrayList<ObjectInstances> - Lista de instancias
	 * @param column int - Posicao do atributo na lista
	 * @return double[] - Vetor [media, desvio padrao]
	 */
	public double[] statisticsByColumn(ArrayList<ObjectInstance> classInstances, int column) {
		ArrayList<Double> columnData = new ArrayList<Double>(); //>Armazena os dados da coluna
		double[] statistics = new double[2]; //> Armazena a media e o desvio padrao calculados
		
		// Convertendo os valores da coluna de String para double e armazenando em columnData
		for(int i = 0; i < classInstances.size(); i++) {
			columnData.add(Double.parseDouble(classInstances.get(i).getAttributes().get(column)));
		}
		
		statistics[0] = Statistics.mean(columnData); // Calculando a media
		statistics[1] = Statistics.stdev(columnData); // Calculando o devio padrao
		
		return statistics;
	}
	
	/**
	 * Calcula a probabilidade de ocorrencia de um atributo nominal para uma determinada classe
	 * @param classInstances ArrayList<ObjectInstances> - Lista de instancias da classe
	 * @param column int - Posicao do atributo na lista
	 * @param attribute String - Valor do atributo
	 * @return double - Probabilidade de ocorrencia do atributo
	 */
	public double probabilityOfNominalAttribute(ArrayList<ObjectInstance> classInstances, int column, String attribute) {
		double count = 0; //> Contagem das ocorrencias do atributo na lista
		
		// Conta as ocorrencias
		for(ObjectInstance instance: classInstances) {
			if(instance.getAttributes().get(column).equals(attribute)){
				count++;
			}
		}
		
		return count / (double) classInstances.size();
	}
	
	/**
	 * Calcula as probabilidades de uma nova instancia pertencer as classes do dataset
	 * @param dataset Dataset - Dataset a ser operado
	 * @param tested String[] - Vetor contendo os valores de atributo da nova instancia
	 * @return ArrayList<Double> - Lista de probabilidades para as classes
	 */
	public ArrayList<Double> calculateClassProbabilities(Dataset dataset, String[] tested) {
		ArrayList<Double> classProbabilities = new ArrayList<Double>(); //> Armazena as probabilidades da nova instancia pertencer as classes do dataset
		ArrayList<Double> attributeProbabilities = new ArrayList<Double>(); //> Armazena temporariamente as probabilidades para cada atributo da instancia, dadas as classes
		double[] statistics;
		ArrayList<ArrayList<ObjectInstance>> separated = separateByClass(dataset);
		
		// Definindo a classe C a ser comparada
		for(int i = 0; i < dataset.getNumClass(); i++) {
			// Calculando as probabilidades para cada atributo da instancia, dada a classe C
			for(int j = 0; j < dataset.getNumAttributes(); j++) {
				if(dataset.getDataTypes()[j] == 0) { // Checando se o atributo e numerico
					statistics = statisticsByColumn(separated.get(i), j);
					attributeProbabilities.add(Statistics.gaussianPdf(Double.parseDouble(tested[j]), statistics[0], statistics[1])); // Normalizando os valores numericos
				} else {
					attributeProbabilities.add(probabilityOfNominalAttribute(separated.get(i), j, tested[j]));
				}
			}
			classProbabilities.add(Statistics.multiplyArrayList(attributeProbabilities) * ((double)classOccurances.get(classes[i]) / dataset.getInstances().size())); // Calculando a estimativa da instancia pertencer a classe C
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
		printResult(calculateClassProbabilities(dataset, tested));
	}
	
	/**
	 * Exibe os resultados da classificação de uma nova instancia
	 * @param classProbabilities ArrayList<Double> - Lista contendo as probabilidades para cada classe
	 */
	public void printResult(ArrayList<Double> classProbabilities) {
		double sum = Statistics.sumArrayList(classProbabilities);

		for(int i = 0; i < classes.length; i++) {
			System.out.println(classes[i] + ": " + (classProbabilities.get(i) / sum)*100 + "%");
		}
	}

	
	/**
	 * Testa a acuracia do classificador sobre um dataset, de forma simplificada
	 * @param dataset Dataset - Dataset a ser operado
	 */
	public void test(Dataset dataset) {
		double correct = 0; //> Numero de instancias classificadas corretamente
		double incorrect = 0; //> Numero de instancias classificadas incorretamente
		
		// Classificando cada instancia do dataset e checando se acertou
		for(ObjectInstance instance : dataset.getInstances()) {
			String[] attributes = new String[dataset.getNumAttributes()];
			attributes = instance.getAttributes().toArray(attributes);
			ArrayList<Double> instanceProbabilities = calculateClassProbabilities(dataset, attributes);
			
			int maxIdx = Statistics.findMaxIdx(instanceProbabilities);
			
			if(instance.getLabel().equals(classes[maxIdx])) {
				correct++;
			} else {
				incorrect++;
			}
		}
		
		System.out.println("correct: " + correct / dataset.getInstances().size());
		System.out.println("incorrect: " + incorrect / dataset.getInstances().size());
	}
}
