package br.com.rotas.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Rotas {
	
	private List<Aresta> arestas;
	
	// vertices associados a uma aresta
	private Set<Vertice> verticesAssociados;
	// vertices NAO associados a uma aresta
	private Set<Vertice> verticesNaoAssociados;
	// vertice pode ser alcancado por qual outro vertice?
	private Map<Vertice, Vertice> precedentes;
	// para cada vertice, armazenar seu preco mais barato
	private Map<Vertice, Integer> precosVertices;

	public Rotas(Grafo grafo) {
		this.arestas = new ArrayList<Aresta>(grafo.getArestas());
	}

	public void buildRotas(Vertice verticeOrigem) {
		verticesAssociados = new HashSet<Vertice>();
		verticesNaoAssociados = new HashSet<Vertice>();
		
		precosVertices = new HashMap<Vertice, Integer>();
		precedentes = new HashMap<Vertice, Vertice>();
		
		precosVertices.put(verticeOrigem, Integer.valueOf(0));
		
		verticesNaoAssociados.add(verticeOrigem);
		
		// Aqui ocorre a definicao de todos os custos para se chegar a todos os demais vertices
		while (!verticesNaoAssociados.isEmpty()) {
			Vertice vertice = getVerticeMenorCusto(verticesNaoAssociados);
			
			verticesAssociados.add(vertice);
			verticesNaoAssociados.remove(vertice);
			
			definirPrecosVerticesAdjacentes(vertice);
		}
	}

	/**
	 * Armazena o menor preco para os vertices adjacentes e 
	 * 
	 * @param vertice
	 */
	private void definirPrecosVerticesAdjacentes(Vertice vertice) {
		List<Vertice> verticesAdjacentes = getVerticesAdjacentes(vertice);
		
		if (verticesAdjacentes != null && !verticesAdjacentes.isEmpty()) {
			for (Vertice verticeAdjacente : verticesAdjacentes) {
				if (getMenorPreco(verticeAdjacente) > getMenorPreco(vertice) + getPrecoAresta(vertice, verticeAdjacente)) {
					
					precosVertices.put(verticeAdjacente, getMenorPreco(vertice) + getPrecoAresta(vertice, verticeAdjacente));
					// o precedente de 'verticeAdjacente' -> 'vertice'
					precedentes.put(verticeAdjacente, vertice);
					
					verticesNaoAssociados.add(verticeAdjacente);
				}
			}
		}
	}

	private Integer getPrecoAresta(Vertice verticeOrigem, Vertice verticeDestino) {
		Integer preco = null;
		
		for (Aresta aresta : arestas) {
			if (aresta.getOrigem().equals(verticeOrigem) && aresta.getDestino().equals(verticeDestino)) {
				preco = aresta.getPreco();
				break;
			}
		}
		
		if (preco == null) {
			throw new RuntimeException("Rota/caminho nao encontrado entre " + verticeOrigem.getId() + " e "
					+ verticeDestino.getId() + ".");
		}
		
		return preco;
	}

	private List<Vertice> getVerticesAdjacentes(Vertice verticeOrigem) {
		List<Vertice> verticesVizinhos = new ArrayList<Vertice>();
		
		for (Aresta aresta : arestas) {
			if (aresta.getOrigem().equals(verticeOrigem) && !verticeAssociado(aresta.getDestino())) {
				verticesVizinhos.add(aresta.getDestino());
			}
		}
		
		return verticesVizinhos;
	}

	private Vertice getVerticeMenorCusto(Set<Vertice> conjuntoVertices) {
		Vertice verticeMaisBarato = null;
		
		for (Vertice vertice : conjuntoVertices) {
			if (verticeMaisBarato == null) {
				verticeMaisBarato = vertice;
			} else {
				if (getMenorPreco(vertice) < getMenorPreco(verticeMaisBarato)) {
					verticeMaisBarato = vertice;
				}
			}
		}
		
		return verticeMaisBarato;
	}

	private boolean verticeAssociado(Vertice vertice) {
		return verticesAssociados.contains(vertice);
	}

	private Integer getMenorPreco(Vertice destino) {
		// o menor preco devera estar em 'precosVertices'
		Integer preco = precosVertices.get(destino);
		
		if (preco == null) {
			return Integer.MAX_VALUE;
		}
		
		return preco;
	}
	
	/**
	 * Retornar o caminho mais economico, percorrendo os precedentes desde o vertice destino. 
	 */
	public List<Vertice> getMelhorCaminho(Vertice verticeDestino) {
		List<Vertice> caminho = new ArrayList<Vertice>();
		Vertice verticePrecedente = verticeDestino;
		
		// Existe algum caminho (precedente) para este no/vertice?
		if (precedentes.get(verticePrecedente) == null) {
			return null;
		}
		
		// adiciono o proprio destino
		caminho.add(verticePrecedente);
		
		while (precedentes.get(verticePrecedente) != null) {
			verticePrecedente = precedentes.get(verticePrecedente);
			caminho.add(verticePrecedente);
		}
		
		// inverter a ordem dos itens da lista
		Collections.reverse(caminho);
		
		return caminho;
	}
	
	/**
	 * Depois de encontrado o caminho mais economico, fazer a soma dos custos 
	 * dos trechos para retorno ao usuario. 
	 */
	public Integer getCustoCaminho(List<Vertice> caminho) {
		Integer custoTotal = Integer.valueOf(0);
		
		for (int i = 1; i < caminho.size(); i++) {
			Vertice vertice = caminho.get(i);
			Integer custoTrecho = getPrecoAresta(precedentes.get(vertice), vertice);
			custoTotal = custoTotal + custoTrecho;
		}
		
		return custoTotal;
	}
}
