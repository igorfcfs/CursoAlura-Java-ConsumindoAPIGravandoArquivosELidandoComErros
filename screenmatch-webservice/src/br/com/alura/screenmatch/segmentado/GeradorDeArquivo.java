package br.com.alura.screenmatch.segmentado;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import br.com.alura.screenmatch.modelos.Titulo;

public class GeradorDeArquivo {
	public void gerarArquivoJson(List<Titulo> titulos, Gson gson) throws IOException {
		
		FileWriter escrita = new FileWriter("filmes.json");
		try {
			escrita.write(gson.toJson(titulos));
		} finally {
			if (escrita != null) escrita.close();
		}
	}
}
