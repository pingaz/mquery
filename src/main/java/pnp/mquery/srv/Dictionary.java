/**
 * 
 */
package pnp.mquery.srv;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @author ping
 *
 */
public class Dictionary {

	private Dictionary parent;
	private final Locale locale;
	
	private Map<String, String> resource = new ConcurrentHashMap<>();

	/**
	 * @param parent
	 * @param resource
	 */
	public Dictionary(Dictionary parent, Locale locale) {
		super();
		this.parent = parent;
		this.locale = locale;
	}

	/**
	 * @param resource
	 */
	public Dictionary(Locale locale) {
		this(null, locale);
	}
	
	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	public String getString(String name) {
		name = name.toLowerCase();
		String res = resource.get(name);
//		if(res==null && parent!=null) {
//			res = parent.getString(name);
//		}
		return res;
	}
	
	public void setString(String name, String value) {
		resource.put(name, value);
	}
	
	public void forEach(BiConsumer<String, String> action) {
        resource.forEach(action);
    }
	
	public Set<String> keySet(){
		return resource.keySet();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dictionary [parent=" + parent + ", locale=" + locale + ", resource=" + resource + "]";
	}
}
