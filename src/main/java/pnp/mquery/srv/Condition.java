/**
 * 
 */
package pnp.mquery.srv;

/**
 * @author ping
 *
 */
public interface Condition {

	/**
	 * To build sql or hql or other ql.
	 * @return
	 */
	public Expression toExpression();
	
	public String getName();
}
