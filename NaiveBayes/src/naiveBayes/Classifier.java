package naiveBayes;

import java.util.ArrayList;

import data.Dataset;

public interface Classifier {
	public String predictInstanceClass(Dataset dataset, String[] tested, ArrayList<Dataset> separated);
	public void printResult(ArrayList<Double> classProbabilities);
	public ArrayList<Dataset> separateByClass(Dataset dataset);
}
