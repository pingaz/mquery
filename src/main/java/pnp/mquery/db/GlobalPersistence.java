/**
 * 
 */
package pnp.mquery.db;

import pnp.mquery.srv.FinderOptions;

/**
 * @author ping
 *
 */
public class GlobalPersistence {
	
	private static final GlobalPersistence instance = new GlobalPersistence();
	
	/**
	 * @return the instance
	 */
	public static GlobalPersistence getInstance() {
		return instance;
	}

	private static final FinderOptions createDefaultOptions() {
		FinderOptions options = new FinderOptions();
		options.setDatabase("h2");
		return options;
	}
	
	private FinderOptions defaultOptions = createDefaultOptions();
	
	private GlobalPersistence() {
		
	}

	public FinderOptions getDefaultOptions() {
		System.out.println("default:"+instance.defaultOptions);
		return instance.defaultOptions;
	}
}
