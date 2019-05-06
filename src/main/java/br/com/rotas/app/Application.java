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
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Application {
	
	private HashMap<String, Vertice> vertices;
	private List<Aresta> arestas;
	
	private static final String DELIMITADOR = ",";
	
	public static void main(String[] args) {
		System.out.println("INICIO");
		
		/*if (args == null || args.length == 0) {
			System.out.println("Favor informar o nome do arquivo.");
			System.exit(-1);
		}*/
		
		//String nomeArquivo = args[0];
		String nomeArquivo = "C:\\Users\\Luis\\Downloads\\github\\rota-viagem\\input-file.txt";
		
		Application app = new Application();
		app.executar(nomeArquivo);
		
		//Scanner scanner = new Scanner(System.in);
		
		//System.out.print("informe o gender: ");
		
		//String gender = scanner.next();
		
		//System.out.println("gender informado: " + gender);
	}
	
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
		System.out.println("Fim da execucao.");
		System.exit(-1);
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
		
		Rotas rotas = new Rotas(grafo);
		
		// definir rotas a partir do vertice informado, calculando todos os custos
		rotas.buildRotas(vertices.get("GRU"));
		
		// obter a rota de menor custo, dado o vertice destino
		List<Vertice> caminho = rotas.getMelhorCaminho(vertices.get("CDG"));
		Integer custoCaminho = rotas.getCustoCaminho(caminho);

		if (caminho == null || caminho.isEmpty()) {
			throw new RuntimeException("NAO ENCONTROU ROTA.");
		}
		
		for (Vertice vertice : caminho) {
			System.out.print(vertice);
			System.out.print("-");
		}
		
		System.out.println("custo do caminho: " + String.valueOf(custoCaminho));
	}
	
	private void addAresta(String arestaId, String verticeOrigem, String verticeDestino, Integer preco) {
		Aresta aresta = new Aresta(arestaId, vertices.get(verticeOrigem), vertices.get(verticeDestino), preco);
		arestas.add(aresta);
	}
}
