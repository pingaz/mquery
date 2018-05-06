/**
 * 
 */
package pnp.mquery.sql;

import java.sql.Connection;

/**
 * @author ping
 *
 */
public interface ConnectionManager {

	Connection getConnection();
	
	void close(Connection c);
}
