/**
 * 
 */
package pnp.mquery.srv;

/**
 * @author ping
 *
 */
public class Expression {
	
	public static final int TYPE_OPERATION = 0x01;
	public static final int TYPE_VALUE = 0x10;
	public static final int TYPE_KEY = 0x100;

	private Expression left;
	private Expression right;
	
	private int type;
	
	private Object value;

	/**
	 * @return the left
	 */
	public Expression getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(Expression left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public Expression getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(Expression right) {
		this.right = right;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
