/**
 * 
 */
package pnp.mquery.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class SimpleSqlQuery implements Query {
	
	private static final Logger log = LogManager.getLogger(SimpleSqlQuery.class);
	
	private final ConnectionManager connMgr;
	private String[] resultKeys;
	private SqlResult result;
	private Object[] values;
	private String sql;
	private String countSql;
	private int size = SqlResult.DEFAULT_RESULT_SIZE;
	
	public SimpleSqlQuery(ConnectionManager connMgr) {
		this.connMgr = connMgr;
	}
	
	public void setValues(Object[] values) {
		this.values = values;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @param countSql the countSql to set
	 */
	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @param resultKeys the resultKeys to set
	 */
	public void setResultKeys(String[] resultKeys) {
		this.resultKeys = resultKeys;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.core.Query#execute()
	 */
	@Override
	public Result execute() {
		if(result==null) {
			Connection conn = null;
			PreparedStatement preparedStatement = null;
			try {
				conn = connMgr.getConnection();
				preparedStatement = conn.prepareStatement(sql);
				if(values!=null) {
					int i=1;
					for(Object v : values) {
						preparedStatement.setObject(i++, v);
					}
				}
				if(resultKeys == null || resultKeys.length == 0) {
					result = SqlResult.buildResult(preparedStatement.executeQuery(), size);
				}else {
					result = SqlResult.buildResult(
							preparedStatement.executeQuery(), resultKeys, size);
				}
				result.setTotal(executeCount(conn));
			} catch (SQLException e) {
				throw new MQException(e);
			} finally {
				try {
					if(preparedStatement!=null) {
						preparedStatement.close();
					}
				} catch (SQLException e) {
					throw new MQException(e);
				}finally {
					if(conn!=null) {
						connMgr.close(conn);
					}
				}
			}
		}
		return result;
	}
	
	private long executeCount(Connection conn) {
		if(countSql==null) {
			return -1;
		}
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = conn.prepareStatement(countSql);
			if(values!=null) {
				int i=1;
				for(Object v : values) {
					preparedStatement.setObject(i++, v);
				}
			}
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				return rs.getLong(1);
			}else {
				return 0;
			}
		} catch (SQLException e) {
			throw new MQException(e);
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
			} catch (SQLException e) {
				throw new MQException(e);
			} finally {
				try {
					if(preparedStatement!=null) {
						preparedStatement.close();
					}
				} catch (SQLException e) {
					throw new MQException(e);
				}
			}
		}
	}

}
