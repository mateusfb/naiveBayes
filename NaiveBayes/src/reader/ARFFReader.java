package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import naiveBayes.Dataset;
import naiveBayes.ObjectInstance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

/** Classe que implementa um leitor de base no formato arff **/
public class ARFFReader implements Reader {

	@Override
	/**
	 * Le uma base no formato arff e armazena seus dados em um Dataset
	 * @param path String - Caminho do arquivo contendo a base de dados
	 * @return Dataset - Dataset contendo as informacoes da base
	 * @throws IOExeption
	 */
	public Dataset read(String path) throws IOException {
		Dataset dataset = new Dataset();
		BufferedReader br = new BufferedReader(new FileReader(path));
		ArffReader arff = new ArffReader(br);
		
		Instances data = arff.getData();
		String[] attributes = new String[data.numAttributes() - 1];
		
		if(data.classIndex() == -1) {
			data.setClassIndex(data.numAttributes() - 1);
		}

		for(int i = 0; i < data.numInstances(); i++) {
		
			for(int j = 0; j < data.numAttributes() -1; j++) {
				if(data.get(i).attribute(j).isNumeric()) {
					attributes[j] = String.valueOf(data.get(i).value(j));
				} else {
					attributes[j] = data.get(i).stringValue(j);
				}
				
			}
			dataset.getInstances().add(new ObjectInstance(new ArrayList<String>(Arrays.asList(attributes)), data.get(i).stringValue(data.get(i).attribute(data.classIndex()))));
		}
		
		dataset.setPath(path);
		dataset.setNumClass(data.numClasses());
		dataset.setNumAttributes(data.numAttributes() - 1);
		return dataset;
	}

}
