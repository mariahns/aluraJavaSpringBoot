package br.com.alura.screenmatch.model;

import java.util.List;
import java.util.OptionalDouble;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.alura.screenmatch.service.Deserializer;

public class Serie {
	
	private String titulo;
	private	Integer totalTemporadas;
	private	Double avaliacao;
	private	Categoria genero;
	private String sinopse;
	private String atores;
	
	public Serie(DadosSerie dadosSerie) {
		this.titulo = dadosSerie.titulo();
		this.totalTemporadas = dadosSerie.totalTemporadas();
		this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
		this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());		
		this.sinopse = dadosSerie.sinopse();		
		this.atores = dadosSerie.atores();
		this.sinopse = dadosSerie.sinopse();
		
		
	}
	


}
