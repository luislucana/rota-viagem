package br.com.rotas.app;

import java.util.HashMap;
import java.util.List;

/**
 * Grafo - Definido como um conjunto de vertices e arestas.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Grafo {
	
	private HashMap<String, Vertice> vertices;

	private List<Aresta> arestas;
	
	public Grafo(HashMap<String, Vertice> vertices, List<Aresta> arestas) {
		this.vertices = vertices;
		this.arestas = arestas;
	}

	public HashMap<String, Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(HashMap<String, Vertice> vertices) {
		this.vertices = vertices;
	}

	public List<Aresta> getArestas() {
		return arestas;
	}

	public void setArestas(List<Aresta> arestas) {
		this.arestas = arestas;
	}
}
