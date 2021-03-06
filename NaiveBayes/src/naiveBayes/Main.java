package naiveBayes;

import java.io.IOException;

import data.Dataset;
import evaluator.Evaluator;
import reader.CSVReader;

public class Main {

	public static void main(String[] args) throws IOException {
		Dataset datasetCsv;
		Evaluator evaluator = new Evaluator();
		
		CSVReader csvReader = new CSVReader();
		datasetCsv = csvReader.read("src/resources/nursery_data.csv");
		datasetCsv.countAttributeOccurances();
		//System.out.println(datasetCsv);
		
		NaiveBayes nb = new NaiveBayes(datasetCsv);
		//nb.classify(datasetCsv, new String[] {"great_pret", "very_crit", "complete", "more", "less_conv", "inconv", "slightly_prob", "not_recom"});
		evaluator.splitTest(datasetCsv, 66, nb);
	}

}
