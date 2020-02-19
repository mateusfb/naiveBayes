package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import naiveBayes.Dataset;
import naiveBayes.ObjectInstance;

public class CSVReader extends Reader {

	@Override
	public Dataset read(String path) throws IOException {
		String row, label;
		String[] data, dataTypes;
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
		
		dataTypes = new String[data.length - 1];
		
		for(int i = 0; i < dataTypes.length; i++) {
			if(isNumeric(data[i])) {
				dataTypes[i] = "number";
			}else {
				dataTypes[i] = "name";
			}
		}
		
		br.close();
		dataset.setDataTypes(dataTypes);
		dataset.setNumClass(set.size());
		
		return dataset;
	}
}
