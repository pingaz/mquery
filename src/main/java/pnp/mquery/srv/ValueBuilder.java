/**
 * 
 */
package pnp.mquery.srv;

import java.util.Map;

/**
 * @author ping
 *
 */
public interface ValueBuilder<T> {

	T build(Map<String, Object> row);
}
