/**
 * 
 */
package pnp.mquery.srv;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pnp.mquery.utils.MessageFormatter;

/**
 * @author ping
 *
 */
public class ValueBuilders {
	
	public static ValueBuilder<String> createTextValueBuilder(String format){
		return new TextValueBuilder(format);
	}
	
	public static class TextValueSerialize extends JsonSerializer<TextValueBuilder>{

		/* (non-Javadoc)
		 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
		 */
		@Override
		public void serialize(TextValueBuilder value, JsonGenerator gen, SerializerProvider serializers)
				throws IOException {
			gen.writeString(value.getFormat());
		}
		
	}
	
	@JsonSerialize(using=TextValueSerialize.class)
	public static class TextValueBuilder implements ValueBuilder<String> {
		
		private final String format;
		private transient final MessageFormatter formatter;
		
		public TextValueBuilder(String format) {
			this.format = format;
			this.formatter = new MessageFormatter(format);
		}

		/**
		 * @return the format
		 */
		public String getFormat() {
			return format;
		}

		/* (non-Javadoc)
		 * @see pnp.mquery.srv.ValueBuilder#build(java.util.Map)
		 */
		@Override
		public String build(Map<String, Object> row) {
			return formatter.format(row);
		}
	}
}
