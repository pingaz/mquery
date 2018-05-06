/**
 * 
 */
package pnp.mquery.srv;

/**
 * @author ping
 *
 */
public class Field {
	private String name;
	private String type;
	private Condition condition;
	private ViewBuilder<?> view;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	/**
	 * @return the view
	 */
	public ViewBuilder<?> getView() {
		return view;
	}
	/**
	 * @param view the view to set
	 */
	public void setView(ViewBuilder<?> view) {
		this.view = view;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Field [name=" + name + ", type=" + type + ", condition=" + condition + ", view=" + view + "]";
	}
}
