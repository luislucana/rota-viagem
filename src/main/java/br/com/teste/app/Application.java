package br.com.teste.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Application {
	
	private HashMap<String, Vertice> vertices;
	private List<Aresta> arestas;
	
	public static void main(String[] args) {
		Application app = new Application();
		app.executar();
	}

	public void executar() {
		vertices = new HashMap<String, Vertice>();
		arestas = new ArrayList<Aresta>();
		
		Vertice verticeGRU = new Vertice("GRU", "GRU");
		Vertice verticeBRC = new Vertice("BRC", "BRC");
		Vertice verticeSCL = new Vertice("SCL", "SCL");
		Vertice verticeCDG = new Vertice("CDG", "CDG");
		Vertice verticeORL = new Vertice("ORL", "ORL");
		
		vertices.put(verticeGRU.getId(), verticeGRU);
		vertices.put(verticeBRC.getId(), verticeBRC);
		vertices.put(verticeSCL.getId(), verticeSCL);
		vertices.put(verticeCDG.getId(), verticeCDG);
		vertices.put(verticeORL.getId(), verticeORL);

		addAresta("GRU-BRC", "GRU", "BRC", 10);
		addAresta("BRC-SCL", "BRC", "SCL", 5);
		addAresta("GRU-CDG", "GRU", "CDG", 75);
		addAresta("GRU-SCL", "GRU", "SCL", 20);
		addAresta("GRU-ORL", "GRU", "ORL", 56);
		addAresta("ORL-CDG", "ORL", "CDG", 5);
		addAresta("SCL-ORL", "SCL", "ORL", 20);

		// Lets check from location Loc_1 to Loc_10
		Grafo grafo = new Grafo(vertices, arestas);
		Rotas rotas = new Rotas(grafo);
		
		rotas.execute(vertices.get("GRU"));
		
		Stack<Vertice> caminho = rotas.getMelhorCaminho(vertices.get("CDG"));

		if (caminho == null || caminho.isEmpty()) {
			throw new RuntimeException("NAO ENCONTROU ROTA!");
		}
		
		for (Vertice vertice : caminho) {
			System.out.print(vertice);
			System.out.print("-");
		}
	}

	private void addAresta(String arestaId, String verticeOrigem, String verticeDestino, Integer distancia) {
		Aresta aresta = new Aresta(arestaId, vertices.get(verticeOrigem), vertices.get(verticeDestino), distancia);
		arestas.add(aresta);
	}
}
