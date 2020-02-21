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
		nb.test(datasetCsv, new double[] {4.7,3.2,1.6,0.2});
		
	}

}
