package naiveBayes;

import java.util.HashMap;

public class NaiveBayes {
	
	private HashMap<String, Integer> classOccurances;
	
	public void buildClassifier(Dataset dataset) {
		
	}
	
	public void countClassOccurances(Dataset dataset){
		for(int i = 0; i < dataset.getInstances().size(); i++) {
			if(classOccurances.containsKey(dataset.getInstances().get(i).getLabel())) {
				classOccurances.put(dataset.getInstances().get(i).getLabel(), classOccurances.get(dataset.getInstances().get(i).getLabel()) + 1);
			}else {
				classOccurances.put(dataset.getInstances().get(i).getLabel(), 1);
			}
		}
	}
	
}
