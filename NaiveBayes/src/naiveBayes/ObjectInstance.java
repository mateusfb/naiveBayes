package naiveBayes;

import java.util.ArrayList;

public class ObjectInstance {
	
	private ArrayList<String> attributes;
	private String label;
	
	public ObjectInstance(ArrayList<String> attributes, String label) {
		this.attributes = attributes;
		this.label = label;
	}

	public ArrayList<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<String> attributes) {
		this.attributes = attributes;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
