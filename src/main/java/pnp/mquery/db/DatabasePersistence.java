/**
 * 
 */
package pnp.mquery.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pnp.mquery.srv.Database;

/**
 * @author ping
 *
 */
public class DatabasePersistence {
	
	private static final Map<String, Database> cacheDatabase = new ConcurrentHashMap<>();

	public void save(Database db) {
		cacheDatabase.put(db.getId(), db);
	}
	
	public Database load(String id) {
		return cacheDatabase.get(id);
	}
}
