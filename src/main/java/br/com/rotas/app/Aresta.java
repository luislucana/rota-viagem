package br.com.rotas.app;


/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Aresta {
	
	private String id;
	
	private Vertice origem;
	
	private Vertice destino;
	
	private Integer preco;
	
	public Aresta(String id, Vertice origem, Vertice destino, Integer preco) {
		this.id = id;
		this.origem = origem;
		this.destino = destino;
		this.preco = preco;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Vertice getOrigem() {
		return origem;
	}

	public void setOrigem(Vertice origem) {
		this.origem = origem;
	}

	public Vertice getDestino() {
		return destino;
	}

	public void setDestino(Vertice destino) {
		this.destino = destino;
	}

	public Integer getPreco() {
		return preco;
	}

	public void setPreco(Integer preco) {
		this.preco = preco;
	}
}
