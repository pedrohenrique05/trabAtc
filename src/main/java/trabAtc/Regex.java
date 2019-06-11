/*
 * Trabalho de Aspectos Teoricos da Computacao
  
 * Grupo:
 * Juarez de Paula Campos Junior - 201676022
 * Leonardo Silva da Cunha - 201676019
 * Pedro Henrique Delgado Moura - 201776032
 */
package trabAtc;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Classe com as operacoes referentes a validacao e leitura de expressoes
 * regulares.
 */
public class Regex {

	private String expressao;
	private char[] valores;
	private int indiceExpressao;
	private String tagInicial;
	private static Stack<String> tags = new Stack<String>();

	/**
	 * Construtor da classe que recebe a expressao como parametro.
	 * 
	 * @param expressao - Expressao que sera utilizada nos metodos.
	 */
	public Regex(String expressao) {
		this.expressao = expressao;
	}

	/**
	 * Metodo que transforma uma string em um array de char.
	 */
	public void separaString() {
		this.valores = expressao.toCharArray();
	}

	/**
	 * Metodo que verifica se a tag inserida antes da expressao regular eh valida ou
	 * nao. O metodo percorre o array de caracteres ate encontrar o caracter ":".
	 * Caso o array comece com espaco ou direto com ":", a tag eh invalida. Caso o
	 * array nao contenha ":", a tag eh invalida. Caso o array nao possua um espaco
	 * apos ":", a tag eh invalida. Se todos os casos acima nao aconteceram a tag eh
	 * valida.
	 * 
	 * @return true se a tag eh valida, false se nao.
	 */
	private boolean validaTag() {
		int i = 0;
		int j = i + 1;
		if (this.valores[0] != ':' && this.valores[0] != ' ') {
			while (i < this.valores.length) {
				if (this.valores[i] == ':')
					if (j != this.valores.length && this.valores[j] == ' ') {
						this.tagInicial += String.valueOf(this.valores[i]);
						break;
					} else {
						System.out.println("[ERRO] EXPRESSAO SEM ESPACO ':'");
						return false;
					}
				this.tagInicial += String.valueOf(this.valores[i]);
				i++;
				j++;
			}
			if (!this.tagInicial.contains(":")) {
				System.out.println("[ERRO] TAG NAO INFORMADA.");
				return false;
			}
		} else {
			System.out.println("[ERRO] NOME DE TAG INVALIDO.");
			return false;
		}

		indiceExpressao = i;
		return true;

	}

	private boolean verificaTag(String tag) {
		if (Regex.tags.contains(tag)) {
			return false;
		} else
			return true;
	}

	/**
	 * Metodo que verifica se a expressao regular em notacao polonesa reversa
	 * passada no construtor da classe eh valida. O metodo separa a String em
	 * caracteres, percorre cada caracter ate achar um operador (. + ou *). Quando
	 * eh encontrado o operador utiliza uma pilha auxiliar para efetuar a
	 * concatenacao dos elementos com os operadores, apos a concatenacao os
	 * elementos concatenados sao empilhados novamente na pilha principal.
	 * 
	 * @return true se a expressao foi valida ou false se a expressao eh invalida.
	 */
	private boolean validaExpressaoRegular() {
		Stack<String> pilha = new Stack<String>();
		Stack<String> pilhaAux = new Stack<String>();
		String comparacao;
		String fechoKleene;
		String concatenacao;

		if (indiceExpressao + 2 >= this.valores.length) {
			System.out.println("[ERRO] EXPRESSAO REGULAR NAO INFORMADA.");
			return false;
		}

		int i = indiceExpressao + 2;

		try {
			do {
				if (this.valores[i] == '+') {
					pilhaAux.push(pilha.pop());
					pilhaAux.push(pilha.pop());
					comparacao = pilhaAux.pop() + this.valores[i] + pilhaAux.pop();
					pilha.push(comparacao);
					i++;
				} else if (this.valores[i] == '*') {
					pilhaAux.push(pilha.pop());
					fechoKleene = pilhaAux.pop() + this.valores[i];
					pilha.push(fechoKleene);
					i++;
				} else if (this.valores[i] == '.') {
					pilhaAux.push(pilha.pop());
					pilhaAux.push(pilha.pop());
					concatenacao = "" + pilhaAux.pop() + pilhaAux.pop();
					pilha.push(concatenacao);
					i++;
				} else {
					pilha.push(String.valueOf(this.valores[i]));
					i++;
				}
			} while (i < this.valores.length);

			if (pilha.size() == 1 && !pilha.contains(" ")) {
				return true;
			} else {
				if (pilha.size() != 1)
					System.out.println("[ERRO] EXPRESSAO REGULAR INVALIDA.");
				else
					System.out.println("[ERRO] A EXPRESSAO REGULAR CONTEM ESPACOS VAZIOS.");
				return false;
			}

		} catch (EmptyStackException e) {
			System.out.println("[ERRO] OPERACOES SEM SIMBOLOS SUFICIENTES.");
			return false;
		}
	}

	/**
	 * Se a tag for valida e nao existir outra tag com o mesmo nome e a expressao
	 * regular for valida, entao a expressao completa eh valida.
	 * 
	 * @return true se expressao eh valida, false se nao.
	 */
	public boolean validaExpressao() {
		separaString();
		if (validaTag() && validaExpressaoRegular()) {
			if (verificaTag(this.tagInicial)) {
				Regex.tags.add(this.tagInicial);
				System.out.println("[INFO] TAG VALIDA.");
				System.out.println("[INFO] EXPRESSAO REGULAR VALIDA.");
				return true;
			} else {
				System.out.println("[ERRO] TAG JA EXISTE.");
				return false;
			}
		} else
			return false;
	}
}
