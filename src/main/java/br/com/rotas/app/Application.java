package br.com.rotas.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		
		Vertice verticeGRU = new Vertice("GRU");
		Vertice verticeBRC = new Vertice("BRC");
		Vertice verticeSCL = new Vertice("SCL");
		Vertice verticeCDG = new Vertice("CDG");
		Vertice verticeORL = new Vertice("ORL");
		
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

		Grafo grafo = new Grafo(vertices, arestas);
		Rotas rotas = new Rotas(grafo);
		
		// definir rotas a partir do vertice informado
		rotas.buildRotas(vertices.get("GRU"));
		
		// obter a rota de menor custo, dado o vertice destino
		List<Vertice> caminho = rotas.getMelhorCaminho(vertices.get("ORL"));

		if (caminho == null || caminho.isEmpty()) {
			throw new RuntimeException("NAO ENCONTROU ROTA.");
		}
		
		for (Vertice vertice : caminho) {
			System.out.print(vertice);
			System.out.print("-");
		}
	}

	private void addAresta(String arestaId, String verticeOrigem, String verticeDestino, Integer preco) {
		Aresta aresta = new Aresta(arestaId, vertices.get(verticeOrigem), vertices.get(verticeDestino), preco);
		arestas.add(aresta);
	}
}
