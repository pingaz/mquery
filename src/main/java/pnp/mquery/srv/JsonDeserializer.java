/**
 * 
 */
package pnp.mquery.srv;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;

import pnp.mquery.db.GlobalPersistence;

/**
 * @author ping
 *
 */
public class JsonDeserializer {

	public static ConditionBuilder deserializeConditionBuilder(JsonNode node) {
        String name = node.get("name").textValue();
        if("single".equalsIgnoreCase(name)) {
        		String key = node.get("key").textValue();
        		String oper = node.get("oper").textValue();
        		SingleConditionBuilder condition = new SingleConditionBuilder(key, oper);
        		return condition;
        } 
        throw new IllegalArgumentException();
	}

	public static ViewBuilder<?> deserializeViewBuilder(JsonNode node) {
        String type = node.get("type").textValue();
        JsonNode valueNode = node.get("value");
        ValueBuilder<String> vvb = valueNode==null || valueNode.isNull()
        		? null : ValueBuilders.createTextValueBuilder(valueNode.textValue());
        JsonNode textNode = node.get("text");
        ValueBuilder<String> tvb = textNode==null || textNode.isNull()
        		? null : ValueBuilders.createTextValueBuilder(textNode.textValue());
        ViewBuilder<?> vb = new ViewBuilder<>(type, vvb, tvb);
        JsonNode optionsNode = node.get("options");
        if(optionsNode!=null && !optionsNode.isNull()) {
        		Map<String, Object> options = new HashMap<>(optionsNode.size());
        		Iterator<Entry<String, JsonNode>> itr = optionsNode.fields();
        		while(itr.hasNext()) {
        			Entry<String, JsonNode> next = itr.next();
        			options.put(next.getKey(), next.getValue().textValue());
        		}
        		vb.setOptions(options);
        }
        return vb;
	}
	
	public static FinderOptions deserializeFinderOptions(JsonNode node) {
		FinderOptions options = new FinderOptions();
		options.setDatabase(node.get("database").textValue());
		options.setDescription(node.get("description").textValue());
		options.setName(node.get("name").textValue());
		options.setTitle(node.get("title").textValue());
		return options;
	}
	
	public static FinderBuilder deserializeFinderBuilder(JsonNode node) {
		FinderBuilder builder = new FinderBuilder();
		builder.setId(node.get("id").textValue());
		builder.setQl(node.get("ql").textValue());
		JsonNode fieldsNode = node.get("fields");
		if(fieldsNode!=null && fieldsNode instanceof ArrayNode) {
			ArrayNode fields = (ArrayNode)fieldsNode;
			FieldBuilder[] fieldBuilders = new FieldBuilder[fields.size()];
			for(int i=0;i<fieldBuilders.length;i++) {
				FieldBuilder b = new FieldBuilder();
				JsonNode j = fields.get(i);
				b.setName(j.get("name").textValue());
				b.setType(j.get("type").textValue());
				JsonNode cnode = j.get("condition");
				if(cnode!=null && !cnode.isNull())
					b.setCondition(JsonDeserializer.deserializeConditionBuilder(cnode));
				JsonNode vnode = j.get("view");
				if(vnode!=null && !vnode.isNull())
					b.setView(JsonDeserializer.deserializeViewBuilder(vnode));
				fieldBuilders[i] = b;
			}
			builder.setFields(fieldBuilders);
		}
		
		JsonNode optionsNode = node.get("options");
		if(optionsNode==null || optionsNode instanceof NullNode) {
			builder.setOptions(GlobalPersistence.getInstance().getDefaultOptions());
		}else {
			builder.setOptions(deserializeFinderOptions(optionsNode));
		}
		return builder;
	}
}
