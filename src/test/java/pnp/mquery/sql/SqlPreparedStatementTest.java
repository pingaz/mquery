/**
 * 
 */
package pnp.mquery.sql;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author ping
 *
 */
class SqlPreparedStatementTest {

	@Test
	void testSingle() throws SQLException {
		String select                  = "select * from test where name like @name and time > @time";
		String selectPrepqredStatement = "select * from test where name like ? and time > ?";
		SqlPreparedStatement sps = new SqlPreparedStatement(select);
		sps.set("name", "testName");
		sps.set("time", new Date(System.currentTimeMillis()));
		sps.build(SqlTestToolkits.getConnection());
		String sql = sps.getBuildedSql();
		System.out.println("SQL - testSingle:"+sql);
		assertEquals(selectPrepqredStatement, sql);
	}

	@Test
	void testMultiple() throws SQLException {
		String insertPreparedStatement = "insert into test (id,name,time) values(?,?,?)";
		String insert                  = "insert into test (@@names) values(@@values)";
		SqlPreparedStatement sps = new SqlPreparedStatement(insert);
		sps.set("id", 123l);
		sps.set("name", "testName");
		sps.set("time", new Date(System.currentTimeMillis()));
		sps.build(SqlTestToolkits.getConnection());
		String sql = sps.getBuildedSql();
		System.out.println("SQL - testMultiple:"+sql);
		assertEquals(insertPreparedStatement, sql);
	}

	@BeforeAll
	public static void initTableData() throws SQLException {
		SqlTestToolkits.initTables();
	}
}
