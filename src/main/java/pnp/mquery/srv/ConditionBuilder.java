/**
 * 
 */
package pnp.mquery.srv;

/**
 * @author ping
 *
 */
public interface ConditionBuilder {

	Condition build();
	Condition build(Object value);
}
