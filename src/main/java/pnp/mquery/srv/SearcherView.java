/**
 * 
 */
package pnp.mquery.srv;

import java.util.Arrays;

/**
 * @author ping
 *
 */
public class SearcherView {

	private String id;
	private int size;
	private Field[] fields;
	private String title;
	private String desc;

	public SearcherView() {
		super();
	}
	public SearcherView(Finder finder) {
		super();
		this.id = finder.getId();
		this.fields = finder.getFields();
		this.title = finder.getOptions().getTitle();
		this.desc = finder.getOptions().getDescription();
		this.size = finder.getSize();
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearcherView [id=" + id + ", fields=" + Arrays.toString(fields) + "]";
	}
}
