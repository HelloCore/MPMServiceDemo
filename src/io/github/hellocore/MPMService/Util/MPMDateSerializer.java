package io.github.hellocore.MPMService.Util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MPMDateSerializer extends JsonSerializer<Date>{

	private static final DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.US); 
	@Override
	  public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
	    jgen.writeString(outputFormatter.format(value));
	  }

}
