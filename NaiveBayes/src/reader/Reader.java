package reader;

import java.io.IOException;

import naiveBayes.Dataset;

public interface Reader {
	
	public abstract Dataset read(String path) throws IOException;

}
