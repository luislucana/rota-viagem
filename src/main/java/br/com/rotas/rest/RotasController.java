package br.com.rotas.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotas.app.Application;
import br.com.rotas.app.GrafoBuilder;

/**
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@RestController
public class RotasController {
	
	@PostMapping(value = "/rota", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Resultado criarRota(@RequestBody RequestRota novaRota) {
		Path path = GrafoBuilder.PATH_ARQUIVO;
		
		try {
			if (rotaInformadaValida(novaRota)) {
				String linhaNovaRota = novaRota.getOrigem() + "," + novaRota.getDestino() + "," + novaRota.getPreco();
				byte[] strToBytes = linhaNovaRota.getBytes();
				
				Files.write(path, strToBytes, StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
			throw new RuntimeException("Nao foi possivel ler o arquivo.");
		}
		
		return new Resultado("Rota criada com sucesso");
	}
	
	@GetMapping(value = "/rota", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Resultado getMelhorRota(@RequestParam String origem, @RequestParam String destino) {
		String melhorRota = "";
		
		melhorRota = Application.builder.obterMelhorCaminho(origem, destino);
		
		return new Resultado(melhorRota);
	}
	
	private static boolean rotaInformadaValida(RequestRota rotaInformada) {
		if (rotaInformada.getOrigem() == null || rotaInformada.getOrigem().trim().length() == 0
				|| rotaInformada.getDestino() == null || rotaInformada.getDestino().trim().length() == 0
				|| rotaInformada.getPreco() == null || rotaInformada.getPreco().trim().length() == 0) {
			throw new IllegalArgumentException("Todos os campos devem ser informados.");
		}		
		
		String origem = rotaInformada.getOrigem();
		String destino = rotaInformada.getDestino();
		
		try {
			Integer.valueOf(rotaInformada.getPreco());
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Parametro invalido (preco deve ser numero inteiro).");
		}
		
		return true;
	}
}
