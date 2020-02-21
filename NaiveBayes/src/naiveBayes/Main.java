package naiveBayes;

import java.io.IOException;

import reader.CSVReader;

public class Main {

	public static void main(String[] args) throws IOException {
		Dataset datasetCsv;
		NaiveBayes nb = new NaiveBayes();
		
		System.out.println("-----------------CSV-----------------");
		CSVReader csvReader = new CSVReader();
		datasetCsv = csvReader.read("src/resources/iris.csv");
		System.out.println(datasetCsv);
		
		nb.buildClassifier(datasetCsv);
		nb.test(datasetCsv, new double[] {5.7,2.9,4.2,1.3});

	}

}
