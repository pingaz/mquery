/**
 * 
 */
package pnp.mquery.sql;

import java.util.Collections;
import java.util.Map;

import pnp.mquery.core.Result;

/**
 * @author ping
 *
 */
public class SingletonResult<T> implements Result {
	
	private final T value;
	private boolean nexted = false;

	/**
	 * @param value
	 */
	public SingletonResult(T value) {
		super();
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#next()
	 */
	@Override
	public boolean next() {
		if(nexted) {
			return false;
		}else {
			nexted = true;
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String key) {
		return value;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#getValues()
	 */
	@Override
	public Map<String, Object> getValues() {
		return Collections.singletonMap("value", value);
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#getKeys()
	 */
	@Override
	public String[] getKeys() {
		return new String[] {"value"};
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#total()
	 */
	@Override
	public long total() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Result#size()
	 */
	@Override
	public int size() {
		return 1;
	}
}
