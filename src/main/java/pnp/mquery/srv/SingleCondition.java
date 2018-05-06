/**
 * 
 */
package pnp.mquery.srv;

/**
 * @author ping
 *
 */
public class SingleCondition<T> implements Condition {
	public static final String OPERATION_LIKE = "like";
	public static final String OPERATION_EQUAL = "eq";
	public static final String OPERATION_NOT_EQUAL = "neq";
	public static final String OPERATION_LESS = "ls";
	public static final String OPERATION_LESS_EQUAL = "leq";
	public static final String OPERATION_GREAT = "gt";
	public static final String OPERATION_GREAT_EQUAL = "geq";
	public static final String OPERATION_IN = "in";
	public static final String OPERATION_BETWEEN = "between";
	
	public static final String TYPE_INTEGER = "int";
	public static final String TYPE_FLOAT = "float";
	public static final String TYPE_STRING = "str";
	public static final String TYPE_DATE = "date";
	public static final String TYPE_TIME = "time";
	public static final String TYPE_DATE_TIME = "dt";
	
	public static final <T> SingleCondition<T> createInstance(String key, String oper, T value){
		return new SingleCondition<>(key, oper, value);
	}
	public static final <T> SingleCondition<T> createEqualInstance(String key, T value){
		return createInstance(key, OPERATION_EQUAL, value);
	}
	public static final <T> SingleCondition<T> createLikeInstance(String key, T value){
		return createInstance(key, OPERATION_LIKE, value);
	}
	public static final <T> SingleCondition<T> createBetweenInstance(String key, T value){
		return createInstance(key, OPERATION_BETWEEN, value);
	}
	
	private String oper;
	private T value;
	private String key;
	
	public SingleCondition() {
		super();
	}

	/**
	 * @param operation
	 * @param value
	 * @param key
	 */
	public SingleCondition(String key, String operation, T value) {
		super();
		this.oper = operation;
		this.value = value;
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
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
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

	/* (non-Javadoc)
	 * @see pnp.mquery.srv.Condition#toExpression()
	 */
	@Override
	public Expression toExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "single";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((oper == null) ? 0 : oper.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		SingleCondition other = (SingleCondition) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (oper == null) {
			if (other.oper != null)
				return false;
		} else if (!oper.equals(other.oper))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SingleCondition [operation=" + oper + ", value=" + value + ", key=" + key + "]";
	}
	
}
