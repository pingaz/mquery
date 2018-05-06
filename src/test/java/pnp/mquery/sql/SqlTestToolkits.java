/**
 * 
 */
package pnp.mquery.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author ping
 *
 */
public class SqlTestToolkits {
	private static final Logger log = LogManager.getLogger(SqlTestToolkits.class);
	
	private static ThreadLocal<Connection> conns = new ThreadLocal<>();

	public static Connection getConnection() {
		Connection c = conns.get();
		if(c==null) {
			try {
				Class.forName("org.h2.Driver").newInstance();
				c = DriverManager.getConnection("jdbc:h2:~/test","sa","");
				conns.set(c);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return c;
	}
	
	public static ConnectionManager CONNECTION_MANAGER = new ConnectionManager() {
		
		@Override
		public Connection getConnection() {
			return SqlTestToolkits.getConnection();
		}
		
		@Override
		public void close(Connection c) {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if(c.isClosed()) {
						conns.remove();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	};

	public static void initTables() throws SQLException {
		log.info("Initailing tables.");
		getConnection().createStatement().execute(
				"CREATE TABLE IF NOT EXISTS TEST("
				+ "id VARCHAR(15) NOT NULL UNIQUE,"
				+ "name VARCHAR(15) NOT NULL,"
				+ "time TIMESTAMP)");
		getConnection().createStatement().execute(
				"CREATE TABLE IF NOT EXISTS TEST_ALL_TYPE("
				+ "id VARCHAR(15) NOT NULL UNIQUE,"
				+ "name VARCHAR(15) NOT NULL,"
				+ "size INT NOT NULL,"
				+ "length DOUBLE NOT NULL,"
				+ "price DECIMAL NOT NULL,"
				+ "CNY DECIMAL NOT NULL,"
				+ "USD DECIMAL NOT NULL,"
				+ "date DATE NOT NULL,"
				+ "time TIME NOT NULL,"
				+ "timestamp TIMESTAMP)");
	}
}
