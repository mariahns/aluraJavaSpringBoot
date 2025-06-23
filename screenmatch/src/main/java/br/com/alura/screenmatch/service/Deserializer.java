package br.com.alura.screenmatch.service;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Deserializer extends JsonDeserializer<List<String>> {
	 
	@Override
	    public List<String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
	        String str = p.getText();
	        return Arrays.stream(str.split(","))
	                     .map(String::trim)
	                     .toList();
	    }

}


