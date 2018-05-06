/**
 * 
 */
package pnp.mquery.sql;


import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import pnp.mquery.core.Result;

/**
 * @author ping
 *
 */
class SqlQueryTest {
	private static final String simpleSqlDeleteAll = "delete from test";
	private static final String simpleSqlInsert = "insert into test (@@names) values(@@values)";
	private static final String simpleSqlSelect = "select * from test where name like @name";
	private static final String simpleSqlKeyName = "name";
	
	private static final String allTypeSqlDeleteAll = "delete from test_all_type";
	private static final String allTypeSqlInsert = "insert into test_all_type (@@names) values (@@values)";
	
	@Test
	void testSimple(){
		SqlQuery query = new SqlQuery(simpleSqlSelect, SqlTestToolkits.CONNECTION_MANAGER);
		try {
			query.set(simpleSqlKeyName, "%");
			Result result = query.execute();
			if(!result.next()) {
				fail("Not one row in result.");
			}else {
				Map<String, Object> values = result.getValues();
				values.forEach((k, v) ->{
					System.out.println(k+":"+v);
				});
			}
		}catch(Exception e){
			fail("Exception cause:"+e.toString());
		}
	}

	@BeforeAll
	public static void initTableData() throws SQLException {
//		LoggingToolkits.setRootLevel(Level.ALL);
		SqlTestToolkits.initTables();
		
		new SqlQuery(simpleSqlDeleteAll, SqlTestToolkits.CONNECTION_MANAGER).executeUpdate();
		for(int i=0;i<200;i++) {
			new SqlQuery(simpleSqlInsert, SqlTestToolkits.CONNECTION_MANAGER)
			.set("id", "test_"+i)
			.set("name", "test "+i)
			.set("time", new Date(System.currentTimeMillis()))
			.executeUpdate();
		}

		new SqlQuery(allTypeSqlDeleteAll, SqlTestToolkits.CONNECTION_MANAGER).executeUpdate();
		for(int i=0;i<20;i++) {
			new SqlQuery(allTypeSqlInsert, SqlTestToolkits.CONNECTION_MANAGER)
			.set("id", "test_"+i)
			.set("name", "test "+i)
			.set("size", i*10)
			.set("length", i*5.5)
			.set("price", new BigDecimal(i*2.5))
			.set("CNY", new BigDecimal(i*10))
			.set("USD", new BigDecimal(i*62.3))
			.set("date", new Date(System.currentTimeMillis()))
			.set("time", new Time(System.currentTimeMillis()))
			.set("timestamp", new Timestamp(System.currentTimeMillis()))
			.executeUpdate();
		}
	}
}
