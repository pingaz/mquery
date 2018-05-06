/**
 * 
 */
package pnp.mquery.sql;

import java.util.List;

/**
 * @author ping
 *
 */
public interface Expression {

	StringBuffer appendSql(StringBuffer ql) ;
	
	void addValue(List<Object> values);
}
