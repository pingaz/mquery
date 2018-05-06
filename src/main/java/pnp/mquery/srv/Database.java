/**
 * 
 */
package pnp.mquery.srv;

import java.util.Properties;

/**
 * @author ping
 *
 */
public class Database {

	private String id;
	private String type;
	private Properties params;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the params
	 */
	public Properties getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(Properties params) {
		this.params = params;
	}
}
