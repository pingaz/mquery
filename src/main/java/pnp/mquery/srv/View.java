/**
 * 
 */
package pnp.mquery.srv;

import java.util.Map;

/**
 * @author ping
 *
 */
public class View <T>{

	private String type;
	private T value;
	private String text;
	private Map<String, Object> options;
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "View [type=" + type + ", value=" + value + ", text=" + text + ", options=" + options + "]";
	}
}
