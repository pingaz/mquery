/**
 * 
 */
package pnp.mquery.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pnp.mquery.core.Result;

/**
 * @author ping
 *
 */
public class SqlResult implements Result {
	
	public static final int DEFAULT_RESULT_SIZE = 100;
	
	public static SqlResult buildResult(ResultSet resultSet, int maxSize) throws SQLException {
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		int i=0;
		try {
			ResultSetMetaData meta = resultSet.getMetaData();
			int count = meta.getColumnCount();
			String[] keys = new String[count];
			for(int column=1;column<=count;column++) {
				keys[column-1] = meta.getColumnName(column).toLowerCase();
			}
			while(resultSet.next() && i++<maxSize) {
				HashMap<String, Object> values = new HashMap<>(count);
				for(int column=1;column<=count;column++) {
					String key = keys[column-1];
					Object value = resultSet.getObject(column);
					values.put(key.toLowerCase(), value);
				}
				list.add(values);
			}
			return new SqlResult(keys, list.iterator(), i);
		}finally {
			resultSet.close();
		}
	}
	
	public static SqlResult buildResult(ResultSet resultSet, String[] keys, int maxSize) throws SQLException {
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		int i=0;
		try {
			while(resultSet.next() && i++<maxSize) {
				HashMap<String, Object> values = new HashMap<>(keys.length);
				for(int column=0;column<keys.length;column++) {
					values.put(keys[column], resultSet.getObject(keys[column]));
				}
				list.add(values);
			}
			return new SqlResult(keys, list.iterator(), i);
		}finally {
			resultSet.close();
		}
	}
	
	public static SqlResult buildResult(ResultSet resultSet, String[] keys) throws SQLException {
		return buildResult(resultSet, keys, 100);
	}
	
	public static SqlResult buildResult(ResultSet resultSet) throws SQLException {
		return buildResult(resultSet, 100);
	}
	
	private final Iterator<Map<String, Object>> result;
	private final int size;
	private final String[] keys;
	private Map<String, Object> next;
	private long total;

	public SqlResult(String[] keys, Iterator<Map<String, Object>> result, int size) {
		this.keys = keys;
		this.result = result;
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#next()
	 */
	@Override
	public boolean next() {
		if(result.hasNext()) {
			this.next = result.next();
		}else {
			this.next = null;
		}
		return this.next != null;
	}

	/**
	 * @return the keys
	 */
	public String[] getKeys() {
		return keys;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String key) {
		return next.get(key);
	}
	
	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#getValues()
	 */
	@Override
	public Map<String, Object> getValues() {
		return next;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#total()
	 */
	@Override
	public long total() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#size()
	 */
	@Override
	public int size() {
		return size;
	}

}
