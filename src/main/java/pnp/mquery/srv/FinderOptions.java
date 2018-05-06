/**
 * 
 */
package pnp.mquery.srv;

import pnp.mquery.MQException;

/**
 * @author ping
 *
 */
public class FinderOptions implements Cloneable {
	
	private String database;
	private String name;
	private String title;
	private String description;

	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @param database
	 *            the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public FinderOptions clone() {
		try {
			return (FinderOptions) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new MQException(e);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FinderOptions [database=" + database + ", name=" + name + ", title=" + title + ", description="
				+ description + "]";
	}
}
