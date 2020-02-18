package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import naiveBayes.Dataset;
import naiveBayes.ObjectInstance;

public class CSVReader implements Reader {

	@Override
	public Dataset read(String path) throws IOException {
		String row, label;
		String[] data;
		ArrayList<String> attributes;
		Dataset dataset = new Dataset(path);
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		
		br.readLine();
		
		while((row = br.readLine()) != null) {
			data = row.split(",");
			
			attributes = new ArrayList<String>(Arrays.asList((Arrays.copyOfRange(data, 0 , data.length - 1)))); 
			label = data[data.length - 1];
			
			dataset.getInstances().add(new ObjectInstance(attributes, label));
		}
		
		br.close();
		
		return dataset;
	}

}
