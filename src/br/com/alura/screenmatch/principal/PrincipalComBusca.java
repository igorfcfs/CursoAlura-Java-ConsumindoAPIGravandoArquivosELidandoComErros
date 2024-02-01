package br.com.alura.screenmatch.principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;

public class PrincipalComBusca {
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Digite um filme para busca: ");
		var busca = sc.nextLine();
		
		String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=8576c31f";
		
		//Request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endereco)).build();
		//Response
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		String json = response.body();
		System.out.println(json);
		
		sc.close();
		
		//SERIALIZAR: transformar estruturas/objetos de memoria em texto (string)
		//DESERIALIZAR: transformar texto em estruturas/objetos de memoria (json, binario, hexadecimal, etc)
		
//		Gson gson = new Gson();
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create(); //resolve o problema das letras maiusculas com boas praticas de programacao, caso as referencias do seu json sejam no padrao PascalCase

//		Titulo meuTitulo = gson.fromJson(json, Titulo.class);
		TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class); //classe record, resolve o problema caso a api seja trocada sem ter que ficar alterando a annotation serializable na classe Titulo
		
		// DIFERENÇA ENTRE CLASSE E RECORD: A diferenca do record TituloOmdb para a classe Titulo e que, na classe Titulo, eu tenho metodos, atributos e um monte de outras coisas necessarias, ja no record TituloOmdb eu so quero transferir dados (Data Transfer Object)
		
//		System.out.println("Titulo: " + meuTitulo.getNome());
		System.out.println(meuTituloOmdb);
		
		Titulo meuTitulo = new Titulo(meuTituloOmdb);
		System.out.println(meuTitulo.getDuracaoEmMinutos());
	}
}