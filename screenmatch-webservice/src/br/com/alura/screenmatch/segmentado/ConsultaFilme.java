package br.com.alura.screenmatch.segmentado;

import static java.net.URLEncoder.encode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaFilme {
	
	public String consultar(String busca) throws IOException, InterruptedException {
		busca = encode(busca);
		System.out.println(busca);
		
		String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=8576c31f";
		System.out.println();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endereco)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		String json = response.body();
		return json;
	}
}
