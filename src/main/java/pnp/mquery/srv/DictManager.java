/**
 * 
 */
package pnp.mquery.srv;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import pnp.mquery.db.DictPersistence;

/**
 * @author ping
 *
 */
public class DictManager {
	
	public void saveString(String finderId, String key, String value, Locale locale) {
		DictPersistence p = new DictPersistence();
		p.setString(finderId, locale, key, value);
	}
	
	public void saveString(String finderId, String key, String value) {
		DictPersistence p = new DictPersistence();
		p.setString(finderId, key, value);
	}
	
	public void saveString(String key, String value, Locale locale) {
		DictPersistence p = new DictPersistence();
		p.setString(null, locale, key, value);
	}
	
	public void saveString(String key, String value) {
		DictPersistence p = new DictPersistence();
		p.setString(null, key, value);
	}

	public String getString(String finderId, String key, Locale locale) {
		if(locale == null || key == null || finderId == null)
			throw new NullPointerException();
		return getStringImpl(finderId, locale, key);
	}

	public String getString(String key, Locale locale) {
		if(locale == null || key == null)
			throw new NullPointerException();
		return getStringImpl(null, locale, key);
	}
	
	protected String getStringImpl(String finderId, Locale locale, String key) {
		DictPersistence p = new DictPersistence();
		String res = StringUtils.isBlank(finderId) ? 
				p.getString(locale, key) : p.getString(finderId, locale, key);
		if(StringUtils.isBlank(res)) {
			return p.getString(locale, key);
		}else {
			return res;
		}
	}
}
