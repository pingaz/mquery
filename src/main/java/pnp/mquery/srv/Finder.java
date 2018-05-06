/**
 * 
 */
package pnp.mquery.srv;

import java.util.Arrays;

/**
 * @author ping
 *
 */
public class Finder {
	
	private String id;

	private Field[] fields;
	
	private FinderOptions options;
	
	private String ql;
	
	private Long offset;
	
	private Integer size;

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
	 * @return the fields
	 */
	public Field[] getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(Field[] fields) {
		this.fields = fields;
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
	 * @return the offset
	 */
	public Long getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Long offset) {
		this.offset = offset;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Finder [id=" + id + ", fields=" + Arrays.toString(fields) + ", options=" + options + ", ql=" + ql + "]";
	}
}
