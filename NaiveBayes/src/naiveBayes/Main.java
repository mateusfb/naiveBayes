package naiveBayes;

import java.io.IOException;

//import reader.ARFFReader;
import reader.CSVReader;

public class Main {

	public static void main(String[] args) throws IOException {
		Dataset datasetCsv;
		//Dataset datasetArff;
		NaiveBayes nb = new NaiveBayes();
		
		//System.out.println("-----------------CSV-----------------");
		CSVReader csvReader = new CSVReader();
		datasetCsv = csvReader.read("src/resources/PlayGolf.csv");
		//datasetCsv = csvReader.read("src/resources/iris.csv");
		//datasetCsv = csvReader.read("src/resources/teste.csv");
		//System.out.println(datasetCsv);
		
		nb.buildClassifier(datasetCsv);
		nb.classify(datasetCsv, new String[] {"Rainy", "Hot", "High"});
		//nb.classify(datasetCsv, new String[] {"6.3","3.3","4.7","1.6"});
		//nb.classify(datasetCsv, new String[] {"3.5","3"});
		//nb.test(datasetCsv);
	}

}
