package br.com.teste.app;


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
	
	private Integer distancia;
	
	public Aresta(String id, Vertice origem, Vertice destino, Integer distancia) {
		this.id = id;
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
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

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
}
