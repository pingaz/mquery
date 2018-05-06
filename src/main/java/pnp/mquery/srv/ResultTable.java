/**
 * 
 */
package pnp.mquery.srv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pnp.mquery.core.Result;

/**
 * @author ping
 *
 */
public class ResultTable {
	
	private transient String finderId;
	private transient Locale locale;
	
	private String[] keys;
	private ArrayList<String> headers;
	private ArrayList<Map<String, Object>> rows;
	private Pagination pagination;
	
	public ResultTable() {
		
	}
	
	public ResultTable(String finderId, Locale locale, Finder finder, Result result) {
		this.finderId = finderId;
		this.locale = locale;
		build(finder, result);
	}
	
	public void build(Finder finder, Result result) {
		Field[] fields = finder.getFields();
		DictManager dm = new DictManager();
		keys = new String[fields.length];
		headers = new ArrayList<>(keys.length);
		for(int i=0;i<keys.length;i++) {
			keys[i] = fields[i].getName();
			String string = dm.getString(finderId, keys[i], locale);
			headers.add(string == null ? keys[i] : string);
		}
		rows = new ArrayList<>(result.size());
		while(result.next()) {
			Map<String, Object> values = result.getValues();
			Map<String, Object> row = new HashMap<>(fields.length);
			for(Field f: fields) {
				if(f.getView()!=null) {
					row.put(f.getName(), f.getView().build(values));
				}else {
					row.put(f.getName(), values.get(f.getName()));
				}
			}
			rows.add(row);
		}
		pagination = new Pagination(finder.getOffset(), result.size(), finder.getSize(), result.total());
	}
	
	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public int size() {
		return rows.size();
	}
	
	public List<String> getHeaders() {
		return headers;
	}

	/**
	 * @return the keys
	 */
	public String[] getKeys() {
		return keys;
	}

	/**
	 * @return the rows
	 */
	public ArrayList<Map<String, Object>> getRows() {
		return rows;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResultTable [keys=" + Arrays.toString(keys) + ", headers=" + headers + ", rows=" + rows + "]";
	}
}