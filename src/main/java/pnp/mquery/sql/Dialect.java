/**
 * 
 */
package pnp.mquery.sql;

/**
 * @author ping
 *
 */
public interface Dialect {

	String getPageQuery(String sql, Long offset, int size);
}
