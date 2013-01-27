package com.pcreations.restclient.test;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class NoteJsonSerializer extends JsonSerializer<Note>{

	@Override
	public void serialize(Note value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
			jgen.writeFieldName("Note");
				jgen.writeStartObject();
					jgen.writeNumberField("address_id", value.getAddress_id());
					jgen.writeStringField("content", value.getContent());
					jgen.writeStringField("privacy", String.valueOf(value.getPrivacy()));
					jgen.writeStringField("problem", String.valueOf(value.getProblem()));
				jgen.writeEndObject();
		jgen.writeEndObject();
		jgen.close();
		
	}
	
}
