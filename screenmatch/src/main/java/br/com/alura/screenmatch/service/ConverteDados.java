package br.com.alura.screenmatch.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.alura.screenmatch.model.DadosSerie;

public class ConverteDados implements IConverteDados {
	
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T> T obterDados(String json, Class<T> classe) {
		// pegar o json e o tipo da classe a ser convertida
		try {
			//retorna classe
			return mapper.readValue(json, classe);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	

}
