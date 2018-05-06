/**
 * 
 */
package pnp.mquery.srv;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author ping
 *
 */
public class FieldBuilder {
	
	public static FieldBuilder createInstance(String key, Class<?> clazz) {
		FieldBuilder f = new FieldBuilder();
		f.setName(key);
		if(Number.class.isAssignableFrom(clazz)) {
			f.setType("number");
			f.setCondition(SingleConditionBuilder.createBetweenInstance(key));
		}else if(String.class.isAssignableFrom(clazz)) {
			f.setType("string");
			f.setCondition(SingleConditionBuilder.createLikeInstance(key));
		}else if(java.sql.Date.class.isAssignableFrom(clazz)) {
			f.setType("date");
			f.setCondition(SingleConditionBuilder.createBetweenInstance(key));
		}else if(Time.class.isAssignableFrom(clazz)) {
			f.setType("time");
			f.setCondition(SingleConditionBuilder.createBetweenInstance(key));
		}else if(Timestamp.class.isAssignableFrom(clazz)) {
			f.setType("datetime");
			f.setCondition(SingleConditionBuilder.createBetweenInstance(key));
		}else if(Date.class.isAssignableFrom(clazz)) {
			f.setType("datetime");
			f.setCondition(SingleConditionBuilder.createBetweenInstance(key));
		}else {
			throw new IllegalArgumentException("Illegal clazz: "+clazz);
		}
		return f;
	}

	private String name;
	private String type;
	private ConditionBuilder condition;
	private ViewBuilder<?> view;
	/**
	 * 
	 */
	public FieldBuilder() {
		super();
	}
	
	/**
	 * @param name
	 * @param type
	 */
	public FieldBuilder(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

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
		this.name = name.toLowerCase();
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
	public ConditionBuilder getCondition() {
		return condition;
	}
	/**
	 * @param condition the condition to set
	 */
	public void setCondition(ConditionBuilder condition) {
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

	public Field build() {
		Field f = new Field();
		f.setName(getName());
		f.setType(getType());
		if(getView()!=null)
			f.setView(getView());
		if(getCondition()!=null)
			f.setCondition(getCondition().build());
		return f;
	}
	public Field build(Object value) {
		Field f = new Field();
		f.setName(getName());
		f.setType(getType());
		if(getView()!=null)
			f.setView(getView());
		if(getCondition()!=null)
			f.setCondition(getCondition().build(value));
		return f;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FieldBuilder [name=" + name + ", type=" + type + ", condition=" + condition + ", view=" + view + "]";
	}
	
}
