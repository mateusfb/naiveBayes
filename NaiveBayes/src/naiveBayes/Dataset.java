package naiveBayes;

import java.util.ArrayList;

public class Dataset {
	
	private ArrayList<ObjectInstance> instances;
	private String path;
	private String[] dataTypes;
	private int numClass;
	@SuppressWarnings("unused")
	private int numAttributes;
	
	public Dataset(String path) {
		this.path = path;
		this.instances = new ArrayList<ObjectInstance>();
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
	
	public String[] getDataTypes() {
		return this.dataTypes;
	}
	
	public void setDataTypes(String[] dataTypes) {
		this.dataTypes = dataTypes;
	}
	
	public int getNumClass() {
		return this.numClass;
	}
	
	public void setNumClass(int numClass) {
		this.numClass = numClass;
	}
	
	public int getNumAttributes() {
		return this.instances.get(0).getAttributes().size();
	}
}
