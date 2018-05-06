/**
 * 
 */
package pnp.mquery.sql;

import pnp.mquery.srv.Condition;
import pnp.mquery.srv.SingleCondition;

/**
 * @author ping
 *
 */
public class ExpressionFactory {

	public Expression create(String database, Condition condition) {
		if(condition instanceof SingleCondition) {
			return new SingleExpression(condition);
		}else {
			throw new IllegalArgumentException(condition.toString());
		}
	}
}
