/**
 * 
 */
package pnp.mquery.srv;

import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pnp.mquery.core.Query;
import pnp.mquery.db.FinderPersistence;

/**
 * @author ping
 *
 */
public class Searcher {
	
	private final static Logger log = LogManager.getLogger(Searcher.class);

	public SearcherView getView(String id) {
		FinderManager fm = new FinderManager();
		Finder finder = fm.buildFinder(id);
		log.info("Get SearcherView finder: {}", finder);
		return new SearcherView(finder);
	}
	
	public ResultTable search(String id) {
		return search(id, null, Locale.getDefault());
	}
	
	public ResultTable search(String id, Locale locale) {
		return search(id, null, locale);
	}
	
	public ResultTable search(String id, Map<String, Object> fieldValues) {
		return search(id, fieldValues, Locale.getDefault());
	}
	
	public ResultTable search(String id, Map<String, Object> fieldValues, Locale locale) {
		return search(id, fieldValues, null, null, locale);
	}
	
	public ResultTable search(String id, Map<String, Object> fieldValues, 
			Long offset, Integer size, Locale locale) {
		FinderManager fm = new FinderManager();
		Finder finder = fm.buildFinder(id, fieldValues, offset, size);
		QueryManager qm = new QueryManager();
		Query query = qm.createQuery(finder);
		ResultTable result = new ResultTable(id, locale, finder, query.execute());
		return result;
	}
	
	public SearcherList list() {
		FinderPersistence p = new FinderPersistence();
		return p.list();
	}
}
