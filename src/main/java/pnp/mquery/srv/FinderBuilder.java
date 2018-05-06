/**
 * 
 */
package pnp.mquery.srv;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pnp.mquery.db.GlobalPersistence;

/**
 * @author ping
 *
 */
public class FinderBuilder {

	private String id;
	
	private String ql;
	
	private Integer pageSize;

	private FieldBuilder[] fields;
	
	private FinderOptions options;
	
	private Map<String, Object> fieldValues ;

	/**
	 * 
	 */
	public FinderBuilder() {
		super();
	}

	/**
	 * @param id
	 * @param ql
	 */
	public FinderBuilder(String id, String ql) {
		super();
		this.id = id;
		this.ql = ql;
	}

	public void addField(String fieldName, Object conditionValue) {
		if(fieldValues == null) {
			fieldValues = new HashMap<>();
		}
		fieldValues.put(fieldName, conditionValue);
	}
	
	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFields(Map<String, Object> fieldValues) {
		this.fieldValues = fieldValues;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the options
	 */
	public FinderOptions getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(FinderOptions options) {
		this.options = options;
	}

	/**
	 * @return the fields
	 */
	public FieldBuilder[] getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(FieldBuilder[] fields) {
		this.fields = fields;
	}

	/**
	 * @return the ql
	 */
	public String getQl() {
		return ql;
	}

	/**
	 * @param ql the ql to set
	 */
	public void setQl(String ql) {
		this.ql = ql;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize!=null && pageSize > 0 ? pageSize : 50;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Finder build() {
		Finder finder = new Finder();
		finder.setId(id);
		finder.setQl(ql);
		finder.setOffset(0L);
		finder.setSize(getPageSize());
		FieldBuilder[] fieldBuilders = getFields();
		if(fieldBuilders!=null) {
			Field[] fields = new Field[getFields().length];
			int i = 0;
			for(FieldBuilder b : getFields()) {
				fields[i++] = buildField(b, b.getName());
			}
			finder.setFields(fields);
		}
		if(options!=null)
			finder.setOptions(options.clone());
		else
			finder.setOptions(GlobalPersistence.getInstance().getDefaultOptions());
		return finder;
	}
	
	private Field buildField(FieldBuilder builder, String name) {
		if(fieldValues==null) {
			return builder.build();
		}else {
			return builder.build(fieldValues.get(name));
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FinderBuilder [id=" + id + ", ql=" + ql + ", fields=" + Arrays.toString(fields) + ", options=" + options
				+ "]";
	}
}
