package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import naiveBayes.Dataset;
import naiveBayes.ObjectInstance;

public class CSVReader implements Reader {

	@Override
	public Dataset read(String path) throws IOException {
		String row, label;
		String[] data;
		ArrayList<String> attributes;
		HashSet<String> set = new HashSet<String>();
		Dataset dataset = new Dataset(path);
		
		BufferedReader br = new BufferedReader(new FileReader(path));
		
		row = br.readLine();
		data = row.split(",");
		
		while((row = br.readLine()) != null) {
			data = row.split(",");
			
			attributes = new ArrayList<String>(Arrays.asList((Arrays.copyOfRange(data, 0 , data.length - 1)))); 
			label = data[data.length - 1];
			set.add(label);
			
			dataset.getInstances().add(new ObjectInstance(attributes, label));
		}
		
		br.close();
		dataset.setNumClass(set.size());
		dataset.setNumAttributes(data.length - 1);
		
		return dataset;
	}
}
