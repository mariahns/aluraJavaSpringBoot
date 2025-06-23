package br.com.alura.screenmatch.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.alura.screenmatch.service.Deserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo, 
						 @JsonAlias("totalSeasons") Integer totalTemporadas, 
						 @JsonAlias("imdbRating") String avaliacao,
					     @JsonAlias("Genre") String genero,
						 @JsonAlias("Plot") String sinopse,
						 //@JsonAlias("Actors") @JsonDeserialize(using = Deserializer.class) List<String> atores
						 @JsonAlias("Actors") String atores,
						 @JsonAlias("Poster") String poster){

}
