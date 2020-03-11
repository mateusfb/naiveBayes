package naiveBayes;

import java.util.ArrayList;
/** Classe responsavel por armazenar as informacoes de uma instancia **/
public class ObjectInstance {
	
	private ArrayList<String> attributes; //> Vetor de atributos da instancia
	private String label; //> Rotulo/Classe da instancia
	
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
