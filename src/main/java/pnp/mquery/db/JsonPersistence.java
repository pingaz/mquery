/**
 * 
 */
package pnp.mquery.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import pnp.mquery.MQException;

/**
 * @author ping
 *
 */
public class JsonPersistence {
	
	private final File parent;

	/**
	 * @param path
	 */
	public JsonPersistence(String path) {
		super();
		this.parent = new File(path);
		if(!this.parent.exists()) {
			this.parent.mkdirs();
		}
	}

	public void save(String fileName, Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
//			mapper.setSerializationInclusion(Include.NON_NULL);
			File file = new File(parent, fileName);
			file.createNewFile();
			mapper.writeValue(file,  object);
		} catch (IOException e) {
			throw new MQException(e);
		}
	}
	
	public JsonNode load(String fileName) {
		return load(new File(parent, fileName));
	}
	
	public JsonNode load(File file) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(file, JsonNode.class);
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException ioe) {
			throw new MQException(ioe);
		}
	}
	
	public JsonNode loadOrCreate(String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			File file = new File(parent, fileName);
			if(!file.exists()) {
				return new JsonNodeFactory(false).objectNode();
			}
			return mapper.readValue(file, JsonNode.class);
		} catch (IOException e) {
			throw new MQException(e);
		}
	}
}
