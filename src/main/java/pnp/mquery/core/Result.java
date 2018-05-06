/**
 * 
 */
package pnp.mquery.core;

import java.util.Map;

/**
 * @author ping
 *
 */
public interface Result {
	
	public long total();
	
	public int size();

	public boolean next();
	
	public Object getValue(String key);
	
	public Map<String, Object> getValues();
	
	public String[] getKeys();
}
