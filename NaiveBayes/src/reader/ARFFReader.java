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

public class ARFFReader implements Reader {

	@Override
	public Dataset read(String path) throws IOException {
		Dataset dataset = new Dataset(path);
		BufferedReader br = new BufferedReader(new FileReader(path));
		ArffReader arff = new ArffReader(br);
		
		Instances data = arff.getData();
		String[] attributes = new String[data.numAttributes() - 1];
		int[] dataTypes = new int[data.numAttributes() -1];
		
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
			dataset.getInstances().add(new ObjectInstance(new ArrayList<String>(Arrays.asList(attributes)), String.valueOf(data.get(i).value(data.classIndex()))));
		}
		
		for(int i = 0; i < dataTypes.length; i++) {
			if(data.get(0).attribute(i).isNumeric()) {
				dataTypes[i] = 0;
			}else {
				dataTypes[i] = 1;
			}
		}
		
		dataset.setDataTypes(dataTypes);
		dataset.setNumClass(data.numClasses());
		dataset.setNumAttributes(data.numAttributes() - 1);
		return dataset;
	}

}
