package naiveBayes;

import java.util.ArrayList;
import java.util.HashMap;

public class Dataset {
	
	private ArrayList<ObjectInstance> instances;
	private String path;
	private int numClass;
	private int numAttributes;
	private ArrayList<HashMap<String, Double>> attributeOccurances;
	
	public Dataset(String path) {
		this.path = path;
		this.instances = new ArrayList<ObjectInstance>();
	}
	
	public void countAttributeOccurances() {
		attributeOccurances = new ArrayList<HashMap<String, Double>>();
	
		for(int i = 0; i < numAttributes; i++) {
			HashMap<String, Double> occurances = new HashMap<>();
			for(ObjectInstance instance: instances) {
				if(occurances.containsKey(instance.getAttributes().get(i))) {
					occurances.put(instance.getAttributes().get(i), occurances.get(instance.getAttributes().get(i)) + 1);
				}else {
					occurances.put(instance.getAttributes().get(i), 1.0);
				}
			}
			
			attributeOccurances.add(occurances);
		}
	}
	
	public String toString() {
		String row = "";
		
		for(int i = 0; i < instances.size(); i++) {
			row += instances.get(i).getAttributes().toString() + instances.get(i).getLabel() + "\n";
		}
		return row;
	}

	public ArrayList<ObjectInstance> getInstances() {
		return instances;
	}

	public void setInstances(ArrayList<ObjectInstance> instances) {
		this.instances = instances;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public int getNumClass() {
		return this.numClass;
	}
	
	public void setNumClass(int numClass) {
		this.numClass = numClass;
	}
	
	public int getNumAttributes() {
		return this.numAttributes;
	}
	
	public void setNumAttributes(int numAttributes) {
		this.numAttributes = numAttributes;
	}

	public ArrayList<HashMap<String, Double>> getAttributeOccurances() {
		return attributeOccurances;
	}

	public void setAttributeOccurances(ArrayList<HashMap<String, Double>> attributeOccurances) {
		this.attributeOccurances = attributeOccurances;
	}
}
