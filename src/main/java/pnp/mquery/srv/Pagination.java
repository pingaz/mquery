/**
 * 
 */
package pnp.mquery.srv;

/**
 * @author ping
 *
 */
public class Pagination {
	private long offset;
	private int size;
	private int pageNumber;
	private int pageSize;
	private int pages;
	private long total;
	/**
	 * 
	 */
	public Pagination() {
		super();
	} 

	/**
	 * @param offset
	 * @param size
	 * @param pageSize
	 * @param total
	 */
	public Pagination(long offset, int size, int pageSize, long total) {
		super();
		this.offset = offset;
		this.size = size;
		this.pageSize = pageSize;
		this.total = total;
		this.pageNumber = ((Double)Math.ceil((double) offset / pageSize)).intValue() + 1;
		this.pages =  ((Double)Math.ceil((double) (offset%pageSize + total) / pageSize)).intValue();
	}

	/**
	 * @return the offset
	 */
	public long getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(long offset) {
		this.offset = offset;
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

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(int pages) {
		this.pages = pages;
	}

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pagination [offset=" + offset + ", size=" + size + ", pageNumber=" + pageNumber + ", pageSize="
				+ pageSize + ", pages=" + pages + ", total=" + total + "]";
	}
	
}
