/*
 * Trabalho de Aspectos Teoricos da Computacao
  
 * Grupo:
 * Juarez de Paula Campos Junior - 201676022
 * Leonardo Silva da Cunha - 201676019
 * Pedro Henrique Delgado Moura- 201776032 
 */

package trabAtc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe com os metodos de manipulacao de arquivos.
 */
public class Arquivo {

	/**
	 * Metodo para escrita em um arquivo.
	 * 
	 * @param expressao  - Expressao que sera salva no arquivo.
	 * @param caminhoArq - Caminho do arquivo.
	 */
	public void setExpressao(String expressao, String caminhoArq) {
		try {
			boolean validador = true;
			File arqCaminho = new File(caminhoArq);
			if (!arqCaminho.exists()) {
				arqCaminho.createNewFile();
				validador = false;
			}
			FileWriter arq = new FileWriter(arqCaminho.getAbsoluteFile(), validador);
			BufferedWriter arqGravar = new BufferedWriter(arq);

			arqGravar.write(expressao + "\r");
			arqGravar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para leitura de dados de um arquivo.
	 * 
	 * @param caminhoArq - Caminho do arquivo que sera lido.
	 * @return - Retorna o conteudo do arquivo.
	 */
	public ArrayList<String> getExpressao(String caminhoArq) {

		ArrayList<String> expressoes = new ArrayList<String>();
		try {
			FileReader entrada = new FileReader(caminhoArq);
			BufferedReader lerEntrada = new BufferedReader(entrada);
			String linha = lerEntrada.readLine();
			while (linha != null) {
				expressoes.add(linha);
				linha = lerEntrada.readLine();

			}
			entrada.close();
		} catch (IOException e) {
			System.out.println("[ERRO] CAMINHO DO ARQUIVO INVALIDO.");
			return null;
		}

		return expressoes;
	}
}