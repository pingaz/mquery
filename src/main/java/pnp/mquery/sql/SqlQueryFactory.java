/**
 * 
 */
package pnp.mquery.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pnp.mquery.core.Query;
import pnp.mquery.srv.Field;
import pnp.mquery.srv.Finder;
import pnp.mquery.srv.QueryFactory;

/**
 * @author ping
 *
 */
public class SqlQueryFactory implements QueryFactory {

	private static final Logger log = LogManager.getLogger(SqlQueryFactory.class);
	
	/**
	 * 
	 */
	public SqlQueryFactory(Properties params) {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.srv.QueryFactory#createQuery(java.lang.String, int)
	 */
	@Override
	public Query createQuery(String ql, int size) {
		SimpleSqlQuery query = new SimpleSqlQuery( CONNECTION_MANAGER );
		query.setSql(ql);
		query.setSize(size);
		return query;
	}

	/* (non-Javadoc)
	 * @see pnp.mquery.srv.QueryFactory#createQuery(pnp.mquery.srv.Finder)
	 */
	@Override
	public Query createQuery(Finder finder) {
		ExpressionFactory ef = new ExpressionFactory();
		SimpleSqlQuery query = new SimpleSqlQuery( CONNECTION_MANAGER);
		String database = finder.getOptions().getDatabase();
		StringBuffer whereSql = new StringBuffer();
		List<Object> values = new ArrayList<>(finder.getFields().length * 2);
		for(Field f:finder.getFields()) {
			if(f.getCondition()!=null) {
				Expression exp = ef.create(database, f.getCondition());
				whereSql = exp.appendSql(whereSql);
				exp.addValue(values);
			}
		}
		String ql = finder.getQl();
		if(whereSql.length()!=0) {
			int index = StringUtils.indexOfIgnoreCase(ql, "where");
			StringBuffer where = new StringBuffer();
			if(index==-1) {
				where.append(" WHERE ").append(whereSql);
			}else {
				where.append(" AND (").append(whereSql).append(") ");
			}
			int groupIndex = StringUtils.indexOfIgnoreCase(ql, "group");
			if(groupIndex == -1) {
				groupIndex = StringUtils.indexOfIgnoreCase(ql, "having");
				if(groupIndex == -1) {
					groupIndex = StringUtils.indexOfIgnoreCase(ql, "order");
					
				}
			}
			if(groupIndex==-1) {
				ql = new StringBuffer(ql).append(where).toString();
			}else {
				String prefix = ql.substring(0, groupIndex-1);
				String suff = ql.substring(groupIndex-1);
				ql = new StringBuffer(prefix).append(where).append(suff).toString();
			}
		}
		//count sql
		String countSql = buildCountSql(ql);
		
		//limit sql
		String dialectQl = DialectFactory.getInstance().getDialect(finder.getOptions().getDatabase())
				.getPageQuery(ql, finder.getOffset(), finder.getSize());
		
		query.setSql(dialectQl);
		query.setCountSql(countSql);
		query.setValues(values.toArray());
		log.info("Dialect QL: {}.", dialectQl);
		log.info("Count QL: {}.", countSql);
		return query;
	}

	private String buildCountSql(String ql) {
		int selectIndex = StringUtils.indexOfIgnoreCase(ql, "select");
		int fromIndex = StringUtils.indexOfIgnoreCase(ql, "from");
		String fromSql = ql.substring(fromIndex-1);
		StringBuilder countSql = new StringBuilder(ql.substring(0, selectIndex+6))
				.append(" count(*)");
		//delete order by
		int orderIndex = StringUtils.indexOfIgnoreCase(fromSql, "order by");
		if(orderIndex==-1) {
			countSql.append(fromSql);
		}else {
			char[] orderQlArray = fromSql.substring(orderIndex+8).toCharArray();
			int bracketCount = 0;
			String endQl = "";
			for(int i=0;i<orderQlArray.length;i++) {
				char c = orderQlArray[i];
				if(c==')' && bracketCount==0) {
					endQl = new String(orderQlArray, i, orderQlArray.length - i);
					break;
				}else if(c==')') {
					bracketCount --;
				}else if(c=='(') {
					bracketCount ++;
				}
			}
			String fromOrderQl = fromSql.substring(0, orderIndex-1);
			countSql.append(fromOrderQl).append(endQl);
		}
		return countSql.toString();
	}
	
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
			return SqlQueryFactory.getConnection();
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
}
