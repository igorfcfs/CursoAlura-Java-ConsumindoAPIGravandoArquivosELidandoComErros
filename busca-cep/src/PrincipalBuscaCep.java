//Versão Procedural

import java.io.FileWriter;
import java.io.IOException;
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

//Cep exemplo: 01001000

public class PrincipalBuscaCep {
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Digite o CEP que deseja buscar: ");
		String busca = sc.nextLine();
		
		String endereco = "https://viacep.com.br/ws/" + busca + "/json";
		
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting().create();
		
		List<String> cepList = new ArrayList<>();
		
		//Request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endereco)).build();
		//Response
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		String json = response.body();
		System.out.println(json);
		
		cepList.add(json);
		
		FileWriter fw = new FileWriter("cep.json");
		fw.write(gson.toJson(cepList));
		fw.close();
		
		sc.close();
	}
}
