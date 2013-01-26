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
		jgen.writeStartArray();
			jgen.writeArrayFieldStart("Note");
			jgen.writeStartArray();
				jgen.writeNumberField("id", value.getId());
				jgen.writeNumberField("address_id", value.getAddress().getId());
				jgen.writeStringField("content", value.getContent());
				jgen.writeStringField("privacy", String.valueOf(value.getPrivacy()));
				jgen.writeStringField("problem", String.valueOf(value.getProblem()));
			jgen.writeEndArray();
		jgen.writeEndArray();
		
	}


}
