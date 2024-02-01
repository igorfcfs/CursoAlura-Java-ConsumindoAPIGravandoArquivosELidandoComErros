package oo;
import java.io.IOException;
import java.util.Scanner;
//cep exemplo: 01001000
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		ConsultaCep consultaCep = new ConsultaCep();
		
		System.out.print("Digite o cep a ser buscado: ");
		String cep = sc.nextLine();
		
		try {			
			Endereco gson = consultaCep.buscaEndereco(cep);
			System.out.println(gson);
			
			GeradorDeArquivo gda = new GeradorDeArquivo();
			gda.gerarArquivoJson(gson);
			
		} catch(RuntimeException | IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
}
