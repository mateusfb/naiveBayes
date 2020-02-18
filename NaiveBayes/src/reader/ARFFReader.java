package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import naiveBayes.Dataset;
import naiveBayes.ObjectInstance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class ARFFReader implements Reader {

	@Override
	public Dataset read(String path) throws IOException {
		Dataset dataset = new Dataset(path);
		ArrayList<String> attributes = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		ArffReader arff = new ArffReader(br);
		
		Instances data = arff.getData();
		
		if(data.classIndex() == -1) {
			data.setClassIndex(data.numAttributes() - 1);
		}

		for(int i = 0; i < data.numInstances(); i++) {
			
			attributes.clear();
			
			for(int j = 0; j < data.numAttributes() -1; j++) {
				attributes.add(String.valueOf(data.get(i).value(j)));
			}
			dataset.getInstances().add(new ObjectInstance(attributes, data.get(i).classAttribute().name()));
		}
		
		return dataset;
	}

}
