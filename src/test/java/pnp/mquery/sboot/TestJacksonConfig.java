/**
 * 
 */
package pnp.mquery.sboot;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import pnp.mquery.srv.Condition;
import pnp.mquery.srv.SingleCondition;

/**
 * @author ping
 *
 */
@Configuration
public class TestJacksonConfig {

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
    		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule module = new SimpleModule();  
        module.addDeserializer(Condition.class, new ConditionDeserializer());  
        objectMapper.registerModule(module);
        return objectMapper;
    }
    
    public static class ConditionDeserializer extends JsonDeserializer<Condition> {  
    	   
        @Override  
        public Condition deserialize(JsonParser jp, DeserializationContext ctxt)   
          throws IOException, JsonProcessingException {  
            JsonNode node = jp.getCodec().readTree(jp);  
            String name = node.get("name").textValue();
            if("single".equalsIgnoreCase(name)) {
            		SingleCondition<?> condition = new SingleCondition<>();
            		condition.setKey(node.get("key").textValue());
            		condition.setOper(node.get("oper").textValue());
            		return condition;
            } 
            throw new IllegalArgumentException();
        }  
    }  
}
