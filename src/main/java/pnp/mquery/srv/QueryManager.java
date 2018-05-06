/**
 * 
 */
package pnp.mquery.srv;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pnp.mquery.core.Query;
import pnp.mquery.sql.SqlQueryFactory;

/**
 * @author ping
 *
 */
public class QueryManager {
	
	private static Map<String, QueryFactory> cacheQueries = new ConcurrentHashMap<>();
	
	public Query createQuery(Finder finder) {
		return getQueryFactory(finder).createQuery(finder);
	}
	
	public Query createQuery(FinderOptions options, String ql, int maxSize) {
		return getQueryFactory(options).createQuery(ql, maxSize);
	}
	
	public QueryFactory getQueryFactory(FinderOptions option) {
		String dbId = option.getDatabase();
		QueryFactory factory = cacheQueries.get(dbId);
		if(factory == null) {
			DatabaseManager dbm = new DatabaseManager();
			Database db = dbm.load(dbId);
			factory = createQueryFactory(db);
			cacheQueries.put(dbId, factory);
		}
		return factory;
	}

	public QueryFactory getQueryFactory(Finder finder) {
		return getQueryFactory(finder.getOptions());
	}
	
	protected QueryFactory createQueryFactory(Database db) {
		switch (db.getType()) {
		case "h2":
			return createSqlQueryFactory(db);
		case "mysql":
			return createSqlQueryFactory(db);
		default:
			throw new IllegalArgumentException();
		}
	}
	
	private SqlQueryFactory createSqlQueryFactory(Database db) {
		return new SqlQueryFactory(db.getParams());
	}
}
