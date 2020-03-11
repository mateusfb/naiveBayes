package reader;

import java.io.IOException;

import naiveBayes.Dataset;

/** Interface que determina um metodo de leitura de base**/
public interface Reader {
	
	/**
	 * Le uma base e armazena seus dados em um Dataset
	 * @param path String - Caminho do arquivo contendo a base de dados
	 * @return Dataset - Dataset contendo as informacoes da base
	 * @throws IOException
	 */
	public abstract Dataset read(String path) throws IOException;

}
