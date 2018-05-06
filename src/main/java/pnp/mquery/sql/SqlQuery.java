/**
 * 
 */
package pnp.mquery.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pnp.mquery.MQException;
import pnp.mquery.core.Query;
import pnp.mquery.core.Result;

/**
 * @author ping
 *
 */
public class SqlQuery implements Query {
	
	private static final Logger log = LogManager.getLogger(SqlQuery.class);
	
	private final ConnectionManager connMgr;
	private final SqlPreparedStatement preparedStatement;
	private String[] resultKeys;
	private SqlResult result;
	
	public SqlQuery(ConnectionManager connMgr) {
		preparedStatement = new SqlPreparedStatement();
		this.connMgr = connMgr;
	}
	
	public SqlQuery(String sql, ConnectionManager connMgr) {
		preparedStatement = new SqlPreparedStatement(sql);
		this.connMgr = connMgr;
	}
	
	public void setSql(String sql) {
		this.preparedStatement.setSql(sql);
	}
	
	public void setResultKeys(String[] keys) {
		this.resultKeys = keys;
	}
	
	public SqlQuery set(String key, Object value) {
		preparedStatement.set(key, value);
		return this;
	}
	
	public int executeUpdate() {
		Connection conn = null;
		try {
			conn = connMgr.getConnection();
			conn.setAutoCommit(true);
			preparedStatement.build(conn);
			log.trace("Execute update sql was: '{}'.", preparedStatement.getBuildedSql());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new MQException(e);
		} finally {
			try {
				if(preparedStatement!=null) {
					preparedStatement.close();
				}
			}finally {
				if(conn!=null) {
					connMgr.close(conn);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Query#execute()
	 */
	public Result execute() {
		if(result==null) {
			Connection conn = null;
			try {
				conn = connMgr.getConnection();
				preparedStatement.build(conn);
				log.info("Keys: " + resultKeys);
				if(resultKeys == null || resultKeys.length == 0) {
					result = SqlResult.buildResult(preparedStatement.executeQuery());
				}else {
					result = SqlResult.buildResult(preparedStatement.executeQuery(), resultKeys);
				}
			} catch (SQLException e) {
				throw new MQException(e);
			} finally {
				try {
					if(preparedStatement!=null) {
						preparedStatement.close();
					}
				}finally {
					if(conn!=null) {
						connMgr.close(conn);
					}
				}
			}
		}
		return result;
	}

}
