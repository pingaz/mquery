/**
 * 
 */
package pnp.mquery.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pnp.mquery.MQException;

/**
 * @author ping
 *
 */
public class SqlPreparedStatement {
	private static final Logger log = LogManager.getLogger(SqlPreparedStatement.class);

	private String sql;
	private final Map<String, Object> keyValues = new LinkedHashMap<>();
	private PreparedStatementBuilder builder;
	private String buildedSql;
	private PreparedStatement buildedStatement;
	
	/**
	 * 
	 */
	public SqlPreparedStatement() {
		super();
	}

	public SqlPreparedStatement(String prepareSql) {
		setSql(prepareSql);
	}
	
	public void setSql(String prepareSql) {
		this.sql = prepareSql;
		this.builder = new PreparedStatementBuilder(sql);
	}
	
	public void set(String key, Object value) {
		keyValues.put(key, value);
	}
	
	public void build(Connection conn) throws SQLException {
		String[] keys = keyValues.keySet().toArray(new String[keyValues.size()]);
		this.buildedSql = builder.build(keys);
		log.trace("Build sql was: '{}'.", buildedSql);
		this.buildedStatement = conn.prepareStatement(this.buildedSql);
		for(int i=0;i<keys.length;i++) {
			this.buildedStatement.setObject(1+i, keyValues.get(keys[i]));
		}
	}
	
	public boolean execute() throws SQLException {
		return this.buildedStatement.execute();
	}
	
	public int executeUpdate() throws SQLException {
		return this.buildedStatement.executeUpdate();
	}
	
	public ResultSet executeQuery() throws SQLException {
		return this.buildedStatement.executeQuery();
	}
	
	/**
	 * @return the buildedSql
	 */
	public String getBuildedSql() {
		return buildedSql;
	}

	public void close() {
		try {
			if(this.buildedStatement!=null && !this.buildedStatement.isClosed()) {
				this.buildedStatement.close();
			}
		} catch (SQLException e) {
			throw new MQException(e);
		} finally {
			this.buildedStatement = null;
		}
	}
	
	class PreparedStatementBuilder{
		Map<String, SqlFragement> fragements = new HashMap<>();
		SqlFragement first;
		public PreparedStatementBuilder(String sql) {
			this.first = new SqlFragement(this);
			this.first.prepare(sql);
		}
		
		public String build(String[] keys) {
			return first.build(new StringBuffer(), keys, keyValues).toString();
		}
	}
	class SqlFragement{
		final PreparedStatementBuilder builder;
		String key;
		String preSql;
		SqlFragement next;
		
		SqlFragement(PreparedStatementBuilder builder){
			this.builder = builder;
		}

		void prepare(String unbuildSql) {
			char[] charArray = unbuildSql.toCharArray();
			int length = charArray.length;
			int nameIndex = -1;
			for(int i=0;i<length;i++) {
				char c = charArray[i];
				if(nameIndex!=-1 && (  c == ' ' || c== ')')) {
					String name = new String(Arrays.copyOfRange(charArray, nameIndex, i));
					String postSql = unbuildSql.substring(i);
					this.key = name;
					builder.fragements.put(name, this);
					prepareNext(postSql);
					return;
				}else if(nameIndex!=-1 && i == length-1) {
					this.key = new String(Arrays.copyOfRange(charArray, nameIndex, i+1));
					builder.fragements.put(this.key, this);
					return;
				}else if(c=='@' && preSql==null) {
					preSql = unbuildSql.substring(0, i);
					nameIndex = i+1;
				}
			}
			preSql = unbuildSql;
		}
		void prepareNext(String postSql) {
			if(postSql.length() > 0) {
				next = new SqlFragement(builder);
				next.prepare(postSql);
			}
		}
		
		StringBuffer build(StringBuffer sqlStatement, String[] keys, Map<String, Object> keyValues) {
			sqlStatement.append(preSql);
			if("@names".equals(this.key)) {
				int length = keys.length;
				if(length != 0 ) {
					sqlStatement.append(keys[0]);
					for(int i=1; i<length;i++) {
						sqlStatement.append(',').append(keys[i]);
					}
				}
				next.build(sqlStatement, keys, keyValues);
			}else if("@values".equals(this.key)){
				int length = keys.length;
				if(length != 0 ) {
					sqlStatement.append('?');
					for(int i=1; i<length;i++) {
						sqlStatement.append(',').append('?');
					}
				}
				next.build(sqlStatement, keys, keyValues);
			}else if(next == null && this.key!=null){
				sqlStatement.append("?");
			}else if(next != null){
				sqlStatement.append("?");
				next.build(sqlStatement, keys, keyValues);
			}
			return sqlStatement;
		}
	}
}
