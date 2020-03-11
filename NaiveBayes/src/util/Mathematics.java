package util;

import java.util.ArrayList;

/** Classe que contem a implementacao de funcoes utilitarias **/
public class Mathematics {
	
	/**
	 * Soma todos os valores de um ArrayList de Double
	 * @param values ArrayList<Double> - ArrayList a ser operado
	 * @return double - Valor da soma
	 */
	public static double sumArrayList(ArrayList<Double> values) {
		double sum = 0;
		
		// Iterando sobre todos os elementos da lista e somando ao valor de sum
		for(Double value : values) {
			sum += value;
		}
		
		return sum;
	}
	
	
	/**
	 * Multiplica todos os valores de um ArrayList de Double
	 * @param values ArrayList<Double> - ArrayList a ser operado
	 * @return double - Valor do produto
	 */
	public static double multiplyArrayList(ArrayList<Double> values) {
		double result = 1;
		
		// Iterando sobre todos os elementos da lista e multiplicando seus valores
		for(double value : values) {
			result = result * value;
		}
		
		return result;
	}
	
	/**
	 * Encontra o maior indice do maior elemento de um ArrayList de Double
	 * @param values ArrayList<Double> - ArrayList a ser operado
	 * @return int - indice do maior elemento da lista
	 */
	public static int findMaxIdx(ArrayList<Double> values) {
		double max = 0;
		int maxIdx = 0;
		
		// Iterando sobre todos os elementos da lista, acompanhando o maior valor e seu indice
		for(int i = 0; i < values.size(); i++) {
			if(max < values.get(i)) {
				max = values.get(i);
				maxIdx = i;
			}
		}
		
		return maxIdx;
	}
}
