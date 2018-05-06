/**
 * 
 */
package pnp.mquery.srv;

/**
 * @author ping
 *
 */
public class DatabaseManager {

	public Database load(String id) {
		Database db = new Database();
		db.setId(id);
		db.setType("h2");
		
		return db;
	}
}
