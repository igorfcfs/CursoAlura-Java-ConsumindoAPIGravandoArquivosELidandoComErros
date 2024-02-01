package br.com.alura.screenmatch.principal;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import br.com.alura.screenmatch.segmentado.ConsultaFilme;
import br.com.alura.screenmatch.segmentado.GeradorDeArquivo;

public class PrincipalComBusca {
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		String busca = "";
		String sair = "";
		List<Titulo> titulos = new ArrayList<>();
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting().create();
		
		try {
			while(!sair.equalsIgnoreCase("s")) {
				System.out.print("Digite um filme para busca: ");
				busca = sc.nextLine();				
				
				ConsultaFilme consulta = new ConsultaFilme();
				String json = consulta.consultar(busca);
				System.out.println(json);
				
				TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
				System.out.println("\n" + meuTituloOmdb);
				
				Titulo meuTitulo = new Titulo(meuTituloOmdb);
				System.out.println("\nTítulo já convertido!");
				System.out.println(meuTitulo + "\n");
				
				titulos.add(meuTitulo);
				System.out.println(titulos + "\n");
				
				GeradorDeArquivo gda = new GeradorDeArquivo();
				gda.gerarArquivoJson(titulos, gson);
				
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