package br.com.alura.screenmatch.principal;

import static java.net.URLEncoder.encode;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;

public class PrincipalComBusca {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		String busca = "";
		String sair = "";
		List<Titulo> titulos = new ArrayList<>();
		
//		Gson gson = new Gson();
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting().create(); //resolve o problema das letras maiusculas com boas praticas de programacao, caso as referencias do seu json sejam no padrao PascalCase
		
		//SERIALIZAR: transformar estruturas/objetos de memoria em texto (string)
		//DESERIALIZAR: transformar texto em estruturas/objetos de memoria (json, binario, hexadecimal, etc)
		
		try {
			while(!sair.equalsIgnoreCase("s")) {
				System.out.print("Digite um filme para busca: ");
				busca = sc.nextLine();				
				
		//		if (busca.contains(" ")) { //resolve o problema dos espacos em branco
		//			busca = busca.replace(" ", "-");
		//		}
				
				busca = encode(busca);
				System.out.println(busca);
				
				String endereco = "https://www.omdbapi.com/?t=" + busca/*.replace(" ", "-")*/ + "&apikey=8576c31f";
				System.out.println();
				
				//Request
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(endereco)).build();
				//Response
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				String json = response.body();
				System.out.println(json);
				
				
	//			Titulo meuTitulo = gson.fromJson(json, Titulo.class);
				TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class); //classe record, resolve o problema caso a api seja trocada sem ter que ficar alterando a annotation serializable na classe Titulo
				
				// DIFERENÇA ENTRE CLASSE E RECORD: A diferenca do record TituloOmdb para a classe Titulo e que, na classe Titulo, eu tenho metodos, atributos e um monte de outras coisas necessarias, ja no record TituloOmdb eu so quero transferir dados (Data Transfer Object)
				
				System.out.println();
	//			System.out.println("Titulo: " + meuTitulo.getNome());
				System.out.println(meuTituloOmdb);
				
				Titulo meuTitulo = new Titulo(meuTituloOmdb);
	//			System.out.println(meuTitulo.getDuracaoEmMinutos());
				System.out.println("\nTítulo já convertido!");
				System.out.println(meuTitulo + "\n");
				
				
//				FileWriter escrita = new FileWriter("filmes.txt");
//				try {
//					escrita.write(json);
//				} finally {
//					if (escrita != null) escrita.close();
//				}
				
				
				titulos.add(meuTitulo);
				
				System.out.println(titulos + "\n");
				
				
				FileWriter escrita = new FileWriter("filmes.json");
				try {
					escrita.write(gson.toJson(titulos));
				} finally {
					if (escrita != null) escrita.close();
				}
				
				
				System.out.print("Deseja sair (s/n)? ");
				sair = sc.nextLine();	
				System.out.println();
			}
		} catch(ConnectException e) {
			System.out.println("\nVoce está sem internet! Tente reconectar o wifi.");
			System.out.println("Erro: " + e.getMessage());
		} catch(NumberFormatException e) {
			System.out.println("\nErro de formato de número: " + e.getMessage());
		} catch(IllegalArgumentException e) {
			System.out.println("Não utilize espaços na hora de buscar um filme, utilize -.\n Erro: " + e.getMessage());
		} catch(ErroDeConversaoDeAnoException e) {
			System.out.println(e.getMessage());
		} finally {
			if(sc != null) {
				sc.close();
			}
		}
	}
}