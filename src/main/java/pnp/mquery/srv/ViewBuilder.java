/**
 * 
 */
package pnp.mquery.srv;

import java.util.Map;

/**
 * @author ping
 *
 */
public class ViewBuilder<T> {

	private String type;
	private ValueBuilder<T> value;
	private ValueBuilder<String> text;
	private Map<String, Object> options;
	/**
	 * 
	 */
	public ViewBuilder() {
		super();
	}
	/**
	 * @param type
	 * @param value
	 * @param text
	 */
	public ViewBuilder(String type, ValueBuilder<T> value, ValueBuilder<String> text) {
		super();
		this.type = type;
		this.value = value;
		this.text = text;
	}
	/**
	 * @param type
	 * @param value
	 * @param text
	 * @param options
	 */
	public ViewBuilder(String type, ValueBuilder<T> value, ValueBuilder<String> text, Map<String, Object> options) {
		super();
		this.type = type;
		this.value = value;
		this.text = text;
		this.options = options;
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
	 * @return the value
	 */
	public ValueBuilder<T> getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(ValueBuilder<T> value) {
		this.value = value;
	}
	/**
	 * @return the text
	 */
	public ValueBuilder<String> getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(ValueBuilder<String> text) {
		this.text = text;
	}
	/**
	 * @return the options
	 */
	public Map<String, Object> getOptions() {
		return options;
	}
	/**
	 * @param options the options to set
	 */
	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
	
	public View<T> build(Map<String, Object> row){
		View<T> view = new View<>();
		view.setOptions(getOptions());
		if(getText()!=null)
			view.setText(getText().build(row));
		view.setType(getType());
		view.setValue(getValue().build(row));
		return view;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ViewBuilder [type=" + type + ", value=" + value + ", text=" + text + ", options=" + options + "]";
	}
}
