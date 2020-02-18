package naiveBayes;

import java.util.ArrayList;

public class Dataset {
	
	private ArrayList<ObjectInstance> instances;
	private String path;
	
	public Dataset(String path) {
		this.path = path;
		this.instances = new ArrayList<ObjectInstance>();
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
	
	
}
