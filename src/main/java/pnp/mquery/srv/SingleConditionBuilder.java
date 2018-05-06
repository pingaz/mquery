/**
 * 
 */
package pnp.mquery.srv;

import static pnp.mquery.srv.SingleCondition.*;

/**
 * @author ping
 *
 */
public class SingleConditionBuilder implements ConditionBuilder {
	
	public static final SingleConditionBuilder createInstance(String key, String oper){
		return new SingleConditionBuilder(key, oper);
	}
	public static final SingleConditionBuilder createEqualInstance(String key){
		return createInstance(key, OPERATION_EQUAL);
	}
	public static final SingleConditionBuilder createLikeInstance(String key){
		return createInstance(key, OPERATION_LIKE);
	}
	public static final SingleConditionBuilder createBetweenInstance(String key){
		return createInstance(key, OPERATION_BETWEEN);
	}

	private String oper;
	private String key;

	/**
	 * @param operation
	 * @param key
	 */
	public SingleConditionBuilder(String key, String operation) {
		super();
		this.oper = operation;
		this.key = key;
	}

	/**
	 * @return the oper
	 */
	public String getOper() {
		return oper;
	}
	/**
	 * @param oper the oper to set
	 */
	public void setOper(String oper) {
		this.oper = oper;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getName() {
		return "single";
	}
	
	/* (non-Javadoc)
	 * @see pnp.mquery.srv.ConditionBuilder#build()
	 */
	@Override
	public Condition build() {
		return SingleCondition.createInstance(key, oper, null);
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.srv.ConditionBuilder#build(java.lang.Object)
	 */
	@Override
	public Condition build(Object value) {
		return SingleCondition.createInstance(key, oper, value);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SingleConditionBuilder [oper=" + oper + ", key=" + key + "]";
	}

}
