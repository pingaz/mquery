/**
 * 
 */
package pnp.mquery.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import org.junit.platform.commons.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import pnp.mquery.srv.Dictionary;

/**
 * @author ping
 *
 */
public class DictPersistence {
	
	private static final Map<Locale, LocaleDicts> cacheDicts = new ConcurrentHashMap<>();
	private static final JsonPersistence jp = new JsonPersistence("datas/dicts/");
	private static final LocaleDicts defaultDicts = loadDefault();
	
	public Dictionary getDictionary(String finderId, Locale locale) {
		LocaleDicts dicts = cacheDicts.get(locale);
		if(dicts == null) {
			dicts = loadLocaleDicts(locale);
		}
		return dicts == null ? null : dicts.getDict(finderId);
	}
	
	public Dictionary getDictionary(Locale locale) {
		LocaleDicts dicts = cacheDicts.get(locale);
		if(dicts == null) {
			dicts = loadLocaleDicts(locale);
		}
		return dicts == null ? null : dicts.getRootDict();
	}
	
	public Dictionary getDictionary(String finderId) {
		return defaultDicts.getDict(finderId);
	}
	
	public Dictionary getDictionary() {
		return defaultDicts.getRootDict();
	}
	
	public String getString(Locale locale, String key) {
		if(isEmptyLocale(locale)) {
			return getString(getDictionary(), key);
		}
		Dictionary d = getDictionary(locale);
		if(d!=null) {
			String res = d.getString(key);
			if(res!=null)
				return res;
		}
		return getString(parentLocale(locale), key);
	}
	
	public String getString(String finderId, Locale locale, String key) {
		if(isEmptyLocale(locale)) {
			return getString(getDictionary(finderId), key);
		}
		Dictionary d = getDictionary(finderId, locale);
		if(d!=null) {
			String res = d.getString(key);
			if(res!=null)
				return res;
		}
		return getString(finderId, parentLocale(locale), key);
	}
	
	private String getString(Dictionary dict, String name) {
		return dict == null ? null : dict.getString(name);
	}
	
	protected Locale parentLocale(Locale locale) {
		if(StringUtils.isNotBlank(locale.getVariant())) {
			return new Locale(locale.getLanguage(), locale.getCountry());
		}else if(StringUtils.isNotBlank(locale.getCountry())) {
			return new Locale(locale.getLanguage());
		}else {
			return Locale.ROOT;
		}
	}
	
	public void setString(String finderId, Locale locale, String key, String string) {
		LocaleDicts ld = cacheDicts.get(locale);
		if(ld == null) {
			ld = new LocaleDicts(locale);
			ld.setRootDict(new Dictionary(locale));
			cacheDicts.put(locale, ld);
		}
		Dictionary root  = ld.getRootDict();
		Dictionary dict;
		if(finderId!=null && !"".equals(finderId.trim())) {
			dict = isEmptyLocale(locale) ? 
					getDictionary(finderId) : getDictionary(finderId, locale);
			if(dict == null) {
				dict = new Dictionary(root, locale);
				ld.putDict(finderId, dict);
			}
		}else {
			dict = root;
		}
		dict.setString(key, string);
		saveLocaleDicts(locale, ld);
	}

	/**
	 * @param locale
	 * @return
	 */
	private boolean isEmptyLocale(Locale locale) {
		return locale == null || Locale.ROOT.equals(locale);
	}
	
	public void setString(String finderId, String key, String string) {
		Dictionary root  = defaultDicts.getRootDict();
		Dictionary dict;
		if(finderId!=null && !"".equals(finderId.trim())) {
			dict = getDictionary(finderId);
			if(dict == null) {
				dict = new Dictionary(root, null);
				defaultDicts.putDict(finderId, dict);
			}
		}else {
			dict = root;
		}
		dict.setString(key.toLowerCase(), string);
		saveLocaleDicts(null, defaultDicts);
	}
	
	private static void saveLocaleDicts(Locale locale, LocaleDicts localeDicts) {
		String fileName = locale == null ? "dict.json" : "dict."+locale.toLanguageTag()+".json";
		Map<String, Object> jsonMap = new HashMap<>();
		Dictionary rootDict = localeDicts.getRootDict();
		rootDict.forEach((k, v)->{
			jsonMap.put(k, v);
		});
		localeDicts.eachLocaleDicts((id, dict)->{
			Map<String, Object> map = new HashMap<>();
			dict.forEach((k, v)->{
				map.put(k, v);
			});
			jsonMap.put(id, map);
		});
		jp.save(fileName, jsonMap);
	}
	
	private static LocaleDicts loadLocaleDicts(Locale locale) {
		JsonNode j = jp.load("dict."+locale.toLanguageTag()+".json");
		if(j!=null) {
			LocaleDicts ld = new LocaleDicts(locale);
			ld.setRootDict(loadDict(ld, null, j));
			cacheDicts.put(locale, ld);
			return ld;
		}
		return null;
	}
	
	private static LocaleDicts loadDefault() {
		JsonNode j = jp.loadOrCreate("dict.json");
		LocaleDicts defaultDicts = new LocaleDicts(null);
		defaultDicts.setRootDict(loadDict(defaultDicts, null, j));
		return defaultDicts;
	}
	
	private static Dictionary loadDict(LocaleDicts localeDicts, Dictionary parent, JsonNode j) {
		Dictionary dict = new Dictionary(parent, localeDicts.getLocale());
		Iterator<Map.Entry<String, JsonNode>> fields = j.fields();
		while(fields.hasNext()) {
			Map.Entry<String, JsonNode> f = fields.next();
			String key = f.getKey();
			JsonNode value = f.getValue();
			if(value instanceof TextNode) {
				dict.setString(key.toLowerCase(), value.textValue());
			}else {
				localeDicts.putDict(key, loadDict(localeDicts, dict, value));
			}
		}
		return dict;
	}
	
	static class LocaleDicts{
		Dictionary rootDict;
		Map<String, Dictionary> cacheLocaleDicts = new ConcurrentHashMap<>();
		final Locale locale;
		
		/**
		 * @param locale
		 */
		public LocaleDicts(Locale locale) {
			super();
			this.locale = locale;
		}

		/**
		 * @return the locale
		 */
		public Locale getLocale() {
			return locale;
		}

		public Dictionary getDict(String id) {
			return cacheLocaleDicts.get(id);
		}
		
		public void putDict(String id, Dictionary dict) {
			cacheLocaleDicts.put(id, dict);
		}
		
		public void eachLocaleDicts(BiConsumer<String, Dictionary> action){
			cacheLocaleDicts.forEach(action);
		}

		/**
		 * @param rootDict the rootDict to set
		 */
		public void setRootDict(Dictionary rootDict) {
			this.rootDict = rootDict;
		}

		/**
		 * @return the rootDict
		 */
		public Dictionary getRootDict() {
			return rootDict;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "LocaleDicts [rootDict=" + rootDict + ", cacheLocaleDicts=" + cacheLocaleDicts + ", locale=" + locale
					+ "]";
		}
	}
}
