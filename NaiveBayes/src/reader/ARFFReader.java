package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import naiveBayes.Dataset;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class ARFFReader implements Reader {

	@Override
	public Dataset read(String path) throws IOException {
		Dataset dataset = new Dataset(path);
		BufferedReader br = new BufferedReader(new FileReader(path));
		ArffReader arff = new ArffReader(br);
		
		Instances data = arff.getStructure();
		
		
		
		return dataset;
	}

}
