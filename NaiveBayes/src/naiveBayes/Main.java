package naiveBayes;

import java.io.IOException;

import reader.ARFFReader;
import reader.CSVReader;

public class Main {

	public static void main(String[] args) throws IOException {
		Dataset datasetArff;
		Dataset datasetCsv;
		
		System.out.println("-----------------CSV-----------------");
		CSVReader csvReader = new CSVReader();
		datasetCsv = csvReader.read("src/resources/iris.csv");
		System.out.println(datasetCsv);
		
		System.out.println("-----------------ARFF-----------------");
		ARFFReader arffReader = new ARFFReader();
		datasetArff = arffReader.read("src/resources/iris.arff");
		System.out.println(datasetArff);
	}

}
