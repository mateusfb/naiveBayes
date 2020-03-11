package naiveBayes;

import java.io.IOException;

import reader.CSVReader;

public class Main {

	public static void main(String[] args) throws IOException {
		Dataset datasetCsv;
		
		CSVReader csvReader = new CSVReader();
		//datasetCsv = csvReader.read("src/resources/PlayGolf.csv");
		datasetCsv = csvReader.read("src/resources/nursery_data.csv");
		datasetCsv.countAttributeOccurances();
		//System.out.println(datasetCsv);
		
		NaiveBayes nb = new NaiveBayes(datasetCsv);
		//nb.classify(datasetCsv, new String[] {"Rainy", "Hot", "High"});
		//nb.classify(datasetCsv, new String[] {"great_pret", "very_crit", "complete", "more", "less_conv", "inconv", "slightly_prob", "not_recom"});
		nb.splitTest(datasetCsv);
	}

}
