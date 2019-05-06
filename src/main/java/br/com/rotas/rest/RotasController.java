package br.com.rotas.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
//@RequestMapping("/rotas")
public class RotasController {
	
	@PostMapping(value = "/rota/{novaRota}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String criarRota(@PathVariable String novaRota) {
		Path path = GrafoBuilder.PATH_ARQUIVO;
		
		try {
			if (rotaInformadaValida(novaRota)) {
				byte[] strToBytes = novaRota.getBytes();
				
				Files.write(path, strToBytes, StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
			throw new RuntimeException("Nao foi possivel ler o arquivo.");
		}
		
		return "Rota criada com sucesso";
	}
	
	@GetMapping(value = "/rota", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public String getMelhorRota(@RequestParam String origem, @RequestParam String destino) {
		String melhorRota = "";
		
		melhorRota = Application.builder.obterMelhorCaminho(origem, destino);
		
		return melhorRota;
	}
	
	private static boolean rotaInformadaValida(String rotaInformada) {
		String[] rota = rotaInformada.split("-");
		
		if (rota.length != 3) {
			return false;
		}
		
		String origem = rota[0];
		String destino = rota[1];
		Integer preco = null;
		
		if (origem.trim().length() == 0) {
			throw new IllegalArgumentException("Conteudo invalido (origem nao deve estar em branco). Cada linha deve estar no formato [ORIGEM],[DESTINO],[PRECO].");
		}
		
		if (destino.trim().length() == 0) {
			throw new IllegalArgumentException("Conteudo invalido (destino nao deve estar em branco). Cada linha deve estar no formato [ORIGEM],[DESTINO],[PRECO].");
		}
		
		try {
			preco = Integer.valueOf(rota[2]);
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Conteudo invalido (preco deve ser numero inteiro). Cada linha deve estar no formato [ORIGEM],[DESTINO],[PRECO].");
		}
		
		return true;
	}
}
