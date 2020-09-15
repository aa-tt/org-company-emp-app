package org.company.empapp.common;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class EmpAppCustomDeserializers {

	/**
	 * custom deserializer to convert the field value, to which it is applied, to
	 * uppercase
	 */
	public static class ToUpperCaseDeserializer extends StdDeserializer<String> {
		private static final long serialVersionUID = 1L;

		public ToUpperCaseDeserializer() {
			this(null);
		}

		public ToUpperCaseDeserializer(Class<?> vc) {
			super(vc);
		}

		@Override
		public String deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			return StringUtils.upperCase(p.getText());
		}
	}

	// Add future custom deserializers here

}
