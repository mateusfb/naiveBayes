package data;

import java.util.ArrayList;
import java.util.HashMap;

/** Classe responsavel por armazenar as informaçoes de uma base de dados **/
public class Dataset {
	
	private ArrayList<ObjectInstance> instances; //> Lista de instancias da base
	private String path; //> Caminho do arquivo da base
	private int numClass; //> Numero de classes da base
	private int numAttributes; //> Numero de atributos da base
	private ArrayList<HashMap<String, Double>> attributeOccurances; //> Lista contendo as ocorrencias de cada possivel atributo, para cada coluna da base
	
	/**
	 * Construtor da classe
	 */
	public Dataset() {
		this.instances = new ArrayList<ObjectInstance>();
	}
	
	
	/**
	 * Conta as ocorrencias de cada possivel atributo da base
	 */
	public void countAttributeOccurances() {
		attributeOccurances = new ArrayList<HashMap<String, Double>>();
	
		// Iterando sobre cada coluna de atributos da base
		for(int i = 0; i < numAttributes; i++) {
			HashMap<String, Double> occurances = new HashMap<>();
			// Iterando sobre cada instancia da base e contando as ocorrencias dos atributos
			for(ObjectInstance instance: instances) {
				if(occurances.containsKey(instance.getValues().get(i))) {
					occurances.put(instance.getValues().get(i), occurances.get(instance.getValues().get(i)) + 1);
				}else {
					occurances.put(instance.getValues().get(i), 1.0);
				}
			}
			
			attributeOccurances.add(occurances);
		}
	}
	
	/**
	 * Retorna uma string representando o dataset
	 * @return String - representacao do dataset
	 */
	public String toString() {
		String data = "";
		
		// Iterando sobre cada linha do dataset e adicionando a string data
		for(int i = 0; i < instances.size(); i++) {
			data += instances.get(i).getValues().toString() + instances.get(i).getLabel() + "\n";
		}
		return data;
	}

	public ArrayList<ObjectInstance> getInstances() {
		return instances;
	}

	public void setInstances(ArrayList<ObjectInstance> instances) {
		this.instances = instances;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public int getNumClass() {
		return this.numClass;
	}
	
	public void setNumClass(int numClass) {
		this.numClass = numClass;
	}
	
	public int getNumAttributes() {
		return this.numAttributes;
	}
	
	public void setNumAttributes(int numAttributes) {
		this.numAttributes = numAttributes;
	}

	public ArrayList<HashMap<String, Double>> getAttributeOccurances() {
		return attributeOccurances;
	}

	public void setAttributeOccurances(ArrayList<HashMap<String, Double>> attributeOccurances) {
		this.attributeOccurances = attributeOccurances;
	}
}
