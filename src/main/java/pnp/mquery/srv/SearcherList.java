/**
 * 
 */
package pnp.mquery.srv;

import java.util.List;

/**
 * @author ping
 *
 */
public class SearcherList {

	private List<Item> itemList;
	private SearcherList folderList;
	
	
	/**
	 * @return the itemList
	 */
	public List<Item> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	/**
	 * @return the folderList
	 */
	public SearcherList getFolderList() {
		return folderList;
	}

	/**
	 * @param folderList the folderList to set
	 */
	public void setFolderList(SearcherList folderList) {
		this.folderList = folderList;
	}

	public static class Item{
		String id;
		String name;
		String title;
		String desc;
		
		/**
		 * 
		 */
		public Item() {
			super();
		}

		public Item(String id, FinderOptions o) {
			this.id = id;
			if(o!=null) {
				this.name = o.getName();
				this.title = o.getTitle();
				this.desc = o.getDescription();
			}
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
	}
}
