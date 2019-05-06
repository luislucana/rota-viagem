package br.com.rotas.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class GrafoBuilder {
	private HashMap<String, Vertice> vertices;
	private List<Aresta> arestas;
	private Rotas rotas = null;
	
	private static final String DELIMITADOR = ",";
	
	private List<String> obterLinhasArquivo(String nomeArquivo) {
		List<String> linhasArquivo = null;
		Path caminho = Paths.get(nomeArquivo);
		try {
			if (!Files.exists(caminho) || !Files.isRegularFile(caminho)) {
				encerrarExecucao("Arquivo " + String.valueOf(caminho) + " nao encontrado.");
			}
			
			if (!Files.isWritable(caminho)) {
				encerrarExecucao("Arquivo " + String.valueOf(caminho) + " nao pode ser editado (permite apenas leitura).");
			}
			
			Stream<String> linhas = Files.lines(caminho);
			linhasArquivo = linhas.collect(Collectors.toList());
			linhas.close();
			
			for (String linha : linhasArquivo) {
				System.out.println(linha);
			}
		} catch (IOException e) {
			encerrarExecucao("Arquivo " + String.valueOf(caminho) + " nao encontrado.");
		}
		
		return linhasArquivo;
	}
	
	private static void encerrarExecucao(String msg) {
		if (msg != null)
			System.out.println(msg);
		//System.out.println("Fim da execucao.");
		//System.exit(-1);
	}
	
	private Grafo buildGrafo(List<String> lines) {
		if (lines == null || lines.isEmpty()) {
			encerrarExecucao("Arquivo esta vazio.");
		}
		
		vertices = new HashMap<String, Vertice>();
		arestas = new ArrayList<Aresta>();
		
		for (String line : lines) {
			// Formato de cada linha: [ORIGEM],[DESTINO],[PRECO]
			String[] values = line.split(DELIMITADOR);
			
			if (values.length != 3) {
				encerrarExecucao("Conteudo invalido. Cada linha deve estar no formato [ORIGEM],[DESTINO],[PRECO].");
			}
			
			String origem = values[0];
			String destino = values[1];
			Integer preco = null;
			
			if (origem.trim().length() == 0) {
				encerrarExecucao("Conteudo invalido (origem nao deve estar em branco). Cada linha deve estar no formato [ORIGEM],[DESTINO],[PRECO].");
			}
			
			if (destino.trim().length() == 0) {
				encerrarExecucao("Conteudo invalido (destino nao deve estar em branco). Cada linha deve estar no formato [ORIGEM],[DESTINO],[PRECO].");
			}
			
			try {
				preco = Integer.valueOf(values[2]);
			} catch (NumberFormatException nfe) {
				encerrarExecucao("Conteudo invalido (preco deve ser numero inteiro). Cada linha deve estar no formato [ORIGEM],[DESTINO],[PRECO].");
			}
			
			vertices.put(origem, new Vertice(origem));
			vertices.put(destino, new Vertice(destino));
			
			addAresta((origem + "-" + destino), origem, destino, preco);
		}
		
		Grafo grafo = new Grafo(vertices, arestas);
		return grafo;
	}

	public void executar(String nomeArquivo) {
		List<String> linhasArquivo = obterLinhasArquivo(nomeArquivo);
		
		Grafo grafo = buildGrafo(linhasArquivo);
		
		rotas = new Rotas(grafo);
		
		//obterMelhorCaminho(rotas, origem, destino);
	}
	
	public String obterMelhorCaminho(String origem, String destino) {
		String melhorCaminho = "";
		
		// definir rotas a partir do vertice informado, calculando todos os custos
		rotas.buildRotas(vertices.get(origem));
		
		// obter a rota de menor custo, dado o vertice destino
		List<Vertice> caminho = rotas.getMelhorCaminho(vertices.get(destino));
		
		Integer custoCaminho = rotas.getCustoCaminho(caminho);

		if (caminho == null || caminho.isEmpty()) {
			encerrarExecucao("NAO ENCONTROU ROTA.");
		}
		
		for (int i = 0; i < caminho.size(); i++) {
			Vertice vertice = caminho.get(i);
			
			if (i != caminho.size() - 1) {
				melhorCaminho = melhorCaminho + "-" + vertice + "-";
			} else {
				melhorCaminho = melhorCaminho + "-" + vertice;
			}
		}
		
		melhorCaminho = melhorCaminho + " > $" + String.valueOf(custoCaminho);
		
		return melhorCaminho;
	}
	
	private void addAresta(String arestaId, String verticeOrigem, String verticeDestino, Integer preco) {
		Aresta aresta = new Aresta(arestaId, vertices.get(verticeOrigem), vertices.get(verticeDestino), preco);
		arestas.add(aresta);
	}
}
