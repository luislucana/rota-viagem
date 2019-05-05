package br.com.teste.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Rotas {
	
	private List<Aresta> arestas;
	
	private Set<Vertice> settledNodes;
	private Set<Vertice> unSettledNodes;
	private Map<Vertice, Vertice> precedentes;
	private Map<Vertice, Integer> distancia;

	public Rotas(Grafo grafo) {
		this.arestas = new ArrayList<Aresta>(grafo.getArestas());
	}

	public void execute(Vertice verticeOrigem) {
		settledNodes = new HashSet<Vertice>();
		unSettledNodes = new HashSet<Vertice>();
		
		distancia = new HashMap<Vertice, Integer>();
		precedentes = new HashMap<Vertice, Vertice>();
		
		distancia.put(verticeOrigem, Integer.valueOf(0));
		
		unSettledNodes.add(verticeOrigem);
		
		while (unSettledNodes.size() > 0) {
			Vertice vertice = getMinimum(unSettledNodes);
			
			settledNodes.add(vertice);
			unSettledNodes.remove(vertice);
			
			findMinimalDistances(vertice);
		}
	}

	private void findMinimalDistances(Vertice vertice) {
		List<Vertice> verticesAdjacentes = getVerticesAdjacentes(vertice);
		
		for (Vertice verticeAdjacente : verticesAdjacentes) {
			if (getShortestDistance(verticeAdjacente) > getShortestDistance(vertice) + getDistance(vertice, verticeAdjacente)) {
				
				distancia.put(verticeAdjacente, getShortestDistance(vertice) + getDistance(vertice, verticeAdjacente));
				
				precedentes.put(verticeAdjacente, vertice);
				
				unSettledNodes.add(verticeAdjacente);
			}
		}
	}

	private int getDistance(Vertice node, Vertice target) {
		Integer distancia = Integer.valueOf(0);
		
		for (Aresta aresta : arestas) {
			if (aresta.getOrigem().equals(node) && aresta.getDestino().equals(target)) {
				distancia = aresta.getDistancia();
				break;
			}
		}
		
		return distancia;
	}

	private List<Vertice> getVerticesAdjacentes(Vertice vertices) {
		List<Vertice> verticesVizinhos = new ArrayList<Vertice>();
		
		for (Aresta aresta : arestas) {
			if (aresta.getOrigem().equals(vertices) && !isSettled(aresta.getDestino())) {
				verticesVizinhos.add(aresta.getDestino());
			}
		}
		
		return verticesVizinhos;
	}

	private Vertice getMinimum(Set<Vertice> vertices) {
		Vertice minimum = null;
		
		for (Vertice vertice : vertices) {
			if (minimum == null) {
				minimum = vertice;
			} else {
				if (getShortestDistance(vertice) < getShortestDistance(minimum)) {
					minimum = vertice;
				}
			}
		}
		
		return minimum;
	}

	private boolean isSettled(Vertice vertice) {
		return settledNodes.contains(vertice);
	}

	private int getShortestDistance(Vertice destino) {
		Integer d = distancia.get(destino);
		
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}
	
	public Stack<Vertice> getMelhorCaminho(Vertice verticeDestino) {
		Stack<Vertice> caminho = new Stack<Vertice>();
		Vertice step = verticeDestino;
		
		// Existe algum caminho (precedente) para este no/vertice?
		if (precedentes.get(step) == null) {
			return null;
		}
		
		// adiciono o proprio destino
		caminho.add(step);
		
		while (precedentes.get(step) != null) {
			step = precedentes.get(step);
			caminho.add(step);
		}
		
		return caminho;
	}
}
