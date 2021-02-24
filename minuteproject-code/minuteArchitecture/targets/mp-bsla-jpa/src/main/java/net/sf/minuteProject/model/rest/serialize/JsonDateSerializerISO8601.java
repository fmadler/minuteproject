package net.sf.minuteProject.model.rest.serialize;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.minuteProject.constant.DateConstants;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import eu.cec.sg.cisnet.framework.FWUtil;

/**
 * This JSON serializer converts date to ISO-8601 (JS) format using the local time zone
 */
public class JsonDateSerializerISO8601 extends JsonSerializer<Date> {
	@Override
	public void serialize(Date date, JsonGenerator jsonGen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jsonGen.writeString(new SimpleDateFormat(DateConstants.FORMAT_DATE_TIME_ISO_8601).format(date));
	}
}