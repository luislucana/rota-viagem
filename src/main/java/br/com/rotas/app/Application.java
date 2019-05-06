package br.com.rotas.app;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@SpringBootApplication
//@EnableAutoConfiguration
@ComponentScan("br.com.rotas.rest.*")
@EntityScan("br.com.rotas.persistence.model")
public class Application {
	
	public static void main(String[] args) {
		
		if (args == null || args.length == 0) {
			System.out.println("Favor informar o nome do arquivo no parametro.");
			System.exit(-1);
		}
		
		String nomeArquivo = args[0];
		//String nomeArquivo = "C:\\Users\\Luis\\Downloads\\github\\rota-viagem\\input-file.txt";
		
		GrafoBuilder builder = new GrafoBuilder();
		builder.executar(nomeArquivo);
		
		SpringApplication.run(Application.class, args);
		
		System.out.println("--------------------------------------------------");
		System.out.println("APLICACAO INICIALIZADA");
		System.out.println("--------------------------------------------------");
		
		while (true) {
			Scanner scanner = new Scanner(System.in);
		    System.out.print("please enter the route: ");
		    String rotaInformada = scanner.next();
		    
		    if (rotaInformadaValida(rotaInformada)) {
		    	// retornar melhor rota
		    	String[] rota = rotaInformada.split("-");
		    	String origem = rota[0];
		    	String destino = rota[1];
		    	builder.obterMelhorCaminho(origem, destino);
		    } else {
		    	System.out.println("A rota informada deve estar no padrao [ORIGEM]-[DESTINO].");
		    }
	
		    System.out.println(rotaInformada);
		    scanner.close();
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
