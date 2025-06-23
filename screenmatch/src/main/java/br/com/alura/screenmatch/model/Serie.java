package br.com.alura.screenmatch.model;

import java.util.List;
import java.util.OptionalDouble;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.alura.screenmatch.service.ConsultaChatGPT;
import br.com.alura.screenmatch.service.Deserializer;

public class Serie {
	
	private String titulo;
	private	Integer totalTemporadas;
	private	Double avaliacao;
	private	Categoria genero;
	private String sinopse;
	private String atores;
	private String poster;
	
	public Serie(DadosSerie dadosSerie) {
		this.titulo = dadosSerie.titulo();
		this.totalTemporadas = dadosSerie.totalTemporadas();
		this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
		this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());		
		this.sinopse = dadosSerie.sinopse();		
		this.atores = dadosSerie.atores();
		//this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
		this.sinopse = dadosSerie.sinopse();
		this.poster = dadosSerie.poster();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getTotalTemporadas() {
		return totalTemporadas;
	}

	public void setTotalTemporadas(Integer totalTemporadas) {
		this.totalTemporadas = totalTemporadas;
	}

	public Double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Double avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Categoria getGenero() {
		return genero;
	}

	public void setGenero(Categoria genero) {
		this.genero = genero;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getAtores() {
		return atores;
	}

	public void setAtores(String atores) {
		this.atores = atores;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Override
	public String toString() {
		return 
				"genero='" + genero + '\'' +
				", titulo='" + titulo + '\'' +
				", totalTemporadas='" + totalTemporadas + '\'' +
				", avaliacao='" + avaliacao + '\'' +
				", atores='" + atores + '\'' +
				", sinopse='" + sinopse + '\'' +
				", poster='" + poster + '\'';				
			
	}
	


}
