package data;

import java.util.ArrayList;
/** Classe responsavel por armazenar as informacoes de uma instancia **/
public class ObjectInstance {
	
	private ArrayList<String> values; //> Vetor de atributos da instancia
	private String label; //> Rotulo/Classe da instancia
	
	public ObjectInstance(ArrayList<String> values, String label) {
		this.values = values;
		this.label = label;
	}

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
