/**
 * 
 */
package pnp.mquery.srv;

/**
 * @author ping
 *
 */
public class Operation {

	public static final String OPERATION_LIKE = "like";
	public static final String OPERATION_EQUAL = "eq";
	public static final String OPERATION_NOT_EQUAL = "neq";
	public static final String OPERATION_LESS = "ls";
	public static final String OPERATION_LESS_EQUAL = "leq";
	public static final String OPERATION_GREAT = "gt";
	public static final String OPERATION_GREAT_EQUAL = "geq";
	
	private String operation;

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
}
