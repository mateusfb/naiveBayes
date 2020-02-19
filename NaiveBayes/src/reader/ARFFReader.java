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
		
		if(data.classIndex() == -1) {
			data.setClassIndex(data.numAttributes() - 1);
		}

		for(int i = 0; i < data.numInstances(); i++) {
		
			for(int j = 0; j < data.numAttributes() -1; j++) {
				attributes[j] = String.valueOf(data.get(i).value(j));
			}
			dataset.getInstances().add(new ObjectInstance(new ArrayList<String>(Arrays.asList(attributes)), String.valueOf(data.get(i).value(data.classIndex()))));
		}
		
		return dataset;
	}

}
