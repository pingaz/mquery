/**
 * 
 */
package pnp.mquery.srv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @author ping
 *
 */
public class SearcherTests extends DataInitialalTest{
	
	@Test
	public void testSearchAll() {
		Map<String, Object> map = new HashMap<>();
		ResultTable result = new Searcher().search(SIMPLE_FINDER_ID, map);
		assertTrue(result.size() >= 10, "Size of result is "+result.size()+", less than 10.");
	}
	
	@Test
	public void testSearchOne() {
		Map<String, Object> map = createSimpleFieldValues();
		ResultTable result = new Searcher().search(SIMPLE_FINDER_ID, map);
		assertTrue(result.size() == 1, "Size of result is "+result.size()+", not is one.");
	}
	
	@Test
	public void testSearchAllOrder() {
		Map<String, Object> map = new HashMap<>();
		ResultTable result = new Searcher().search(TEST_ORDER_FINDER_ID, map);
		assertTrue(result.size() >= 10, "Size of result is "+result.size()+", less than 10.");
	}
	
	@Test
	public void testSearchOneOrder() {
		Map<String, Object> map = createSimpleFieldValues();
		ResultTable result = new Searcher().search(TEST_ORDER_FINDER_ID, map);
		assertTrue(result.size() == 1, "Size of result is "+result.size()+", not is one.");
	}

	@Test
	public void testSearchAllType() {
		ResultTable result = new Searcher().search(TEST_ALL_TYPE_FINDER_ID);
		assertTrue(result.size() == 20, "Size of result is "+result.size()+", not is 20.");
		assertEquals(result.getKeys().length, 10);
	}
	
	@Test
	public void testSearchAllTypeByListArguments() {
		HashMap<String, Object> values = new HashMap<>();
		values.put("size", Arrays.asList(new Integer[] {0, 1000}));
		ResultTable result = new Searcher().search(TEST_ALL_TYPE_FINDER_ID, values);
		assertTrue(result.size() == 20, "Size of result is "+result.size()+", not is 20.");
		assertEquals(result.getKeys().length, 10);
	}
	
	@Test
	public void testSearchValueType() {
		HashMap<String, Object> values = new HashMap<>();
		ResultTable result = new Searcher().search(TEST_VALUE_FINDER_ID, values, Locale.CHINA);
		assertTrue(result.size() == 20, "Size of result is "+result.size()+", not is 20.");
		assertEquals(result.getKeys().length, 3);
		Map<String, Object> row = result.getRows().get(0);
		View<?> btnDelete = (View<?>) row.get("delete_btn");
		System.out.println("rows:"+row);
		assertEquals(String.class, btnDelete.getValue().getClass());
		assertEquals("DELETE", btnDelete.getText());
		assertEquals(row.get("id"), btnDelete.getValue());
	}
	
	@Test
	public void testSearchWithIndexAndOffset() {
		HashMap<String, Object> values = new HashMap<>();
		ResultTable result = new Searcher().search(TEST_VALUE_FINDER_ID, values, 10L, 1, Locale.CHINA);
		assertTrue(result.size() == 1, "Size of result is "+result.size()+", not is 1.");
		assertEquals(10L, result.getPagination().getOffset());
		assertEquals(1, result.getPagination().getSize());
		assertEquals(20L, result.getPagination().getTotal());
		
		ResultTable noLimitResult = new Searcher().search(TEST_VALUE_FINDER_ID, values, Locale.CHINA);
		assertTrue(noLimitResult.size() == 20, "Size of result is "+result.size()+", not is 20.");
		assertEquals(0L, noLimitResult.getPagination().getOffset());
		assertEquals(20, noLimitResult.getPagination().getSize());
		assertEquals(20L, noLimitResult.getPagination().getTotal());
	}
}
