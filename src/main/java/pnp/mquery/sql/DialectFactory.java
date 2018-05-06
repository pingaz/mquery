/**
 * 
 */
package pnp.mquery.sql;

/**
 * @author ping
 *
 */
public class DialectFactory {
	
	private static final DialectFactory instance = new DialectFactory();
	
	/**
	 * @return the instance
	 */
	public static DialectFactory getInstance() {
		return instance;
	}

	private DialectFactory() {
		
	}

	public Dialect getDialect(String database) {
		if("h2".equals(database)) {
			return new H2Dialect();
		}else {
			throw new IllegalArgumentException();
		}
	}
}
