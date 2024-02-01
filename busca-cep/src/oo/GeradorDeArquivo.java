package oo;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeradorDeArquivo {
	
	public void gerarArquivoJson(Endereco endereco) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter fw = new FileWriter(endereco.cep() + ".json");
		fw.write(gson.toJson(endereco));
		fw.close();
	}
}
