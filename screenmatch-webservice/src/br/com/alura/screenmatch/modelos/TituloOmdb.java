package br.com.alura.screenmatch.modelos;

//Em um record nos somente declaramos um cabecalho
//o problema esta na letra minuscula, teria de ser maiuscula para funcionar, porem, nao e uma boa pratica de programacao
public record TituloOmdb(String title, String year, String runtime) {}
