/**
 * 
 */
package pnp.mquery.srv;

import pnp.mquery.core.Query;

/**
 * @author ping
 *
 */
public interface QueryFactory {

	Query createQuery(Finder finder);
	
	Query createQuery(String ql, int size);
}
