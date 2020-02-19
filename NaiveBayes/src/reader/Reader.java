package reader;

import java.io.IOException;

import naiveBayes.Dataset;

public abstract class Reader {
	
	public abstract Dataset read(String path) throws IOException;
		
	public boolean isNumeric(String str) {
		for(int i = 0; i < str.length(); i++) {
			if(!(Character.isDigit(str.charAt(i)))) {
				if(str.charAt(i) != '.') {
					return false;
				}
			}
		}
		
		return true;
	}

}
