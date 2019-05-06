package br.com.rotas.rest;

public class Resultado {
	private String resultado;
	
	public Resultado(String mensagem) {
		this.resultado = mensagem;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
}
