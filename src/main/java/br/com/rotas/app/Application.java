package br.com.rotas.app;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@SpringBootApplication
//@EnableAutoConfiguration
@ComponentScan("br.com.rotas.*")
public class Application {
	
	public static GrafoBuilder builder = new GrafoBuilder();
	
	public static void main(String[] args) {
		
		if (args == null || args.length == 0) {
			System.out.println("Favor informar o nome do arquivo no parametro.");
			System.exit(-1);
		}
		
		String nomeArquivo = args[0];
		
		builder.executar(nomeArquivo);
		
		SpringApplication.run(Application.class, args);
		
		System.out.println("--------------------------------------------------");
		System.out.println("APLICACAO INICIALIZADA");
		System.out.println("--------------------------------------------------");
		
		String melhorCaminho = "";
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (true) {
		    System.out.print("please enter the route: ");
		    String rotaInformada = scanner.nextLine();
		    
		    if (rotaInformadaValida(rotaInformada)) {
		    	// retornar melhor rota
		    	String[] rota = rotaInformada.split("-");
		    	String origem = rota[0];
		    	String destino = rota[1];
		    	
		    	melhorCaminho = builder.obterMelhorCaminho(origem, destino);
		    } else {
		    	System.out.println("A rota informada deve estar no padrao [ORIGEM]-[DESTINO].");
		    }
	
		    System.out.println(melhorCaminho);
		}
	}
	
	private static boolean rotaInformadaValida(String rotaInformada) {
		boolean rotaValida = false;
		
		String[] rota = rotaInformada.split("-");
		
		if (rota.length == 2) {
			rotaValida = true;
		}
		
		return rotaValida;
	}
}
