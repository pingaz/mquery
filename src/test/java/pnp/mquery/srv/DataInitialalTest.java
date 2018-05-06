/**
 * 
 */
package pnp.mquery.srv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author ping
 *
 */
public abstract class DataInitialalTest {

    /**
	 * 
	 */
	private static final Date[] SIMPLE_TEST_DATES = new Date[] {new Date(0), new Date()};

	/**
	 * 
	 */
	private static final String SIMPLE_TEST_NAME = "%test%";

	/**
	 * 
	 */
	private static final String SIMPLE_TEST_ID = "test_10";

	public static final Logger log = LogManager.getLogger(DataInitialalTest.class);
	
	public static final String SIMPLE_FINDER_ID = "pnp.mquery.finder.test01";
	public static final String SIMPLE_FINDER_QL = "SELECT * FROM test";

	public static final String TEST_ORDER_FINDER_ID = "pnp.mquery.finder.test_order";
	public static final String TEST_ORDER_FINDER_QL = "SELECT * FROM test ORDER BY id";
	
	public static final String TEST_ALL_TYPE_FINDER_ID = "test_all_type";
	public static final String TEST_ALL_TYPE_FINDER_QL = "SELECT * FROM TEST_ALL_TYPE";
	
	public static final String TEST_VALUE_FINDER_ID = "test_render";
	public static final String TEST_VALUE_FINDER_QL = "SELECT * FROM TEST_ALL_TYPE";
	
	public Map<String, Object> createSimpleFieldValues(){

		Map<String, Object> map = new HashMap<>();
		String testId = SIMPLE_TEST_ID;
		String testName = SIMPLE_TEST_NAME;
		Date[] testDates = SIMPLE_TEST_DATES;
		map.put("id", testId);
		map.put("name", testName);
		map.put("time", testDates);
		
		return map;
	}
	
	public static void assertSimpleFieldBuilders(FieldBuilder[] fields) {
		for(FieldBuilder f : fields) {
			SingleConditionBuilder condition = (SingleConditionBuilder)f.getCondition();
			String name = f.getName();
			switch (name) {
			case "id":
				assertEquals(condition.getOper(), SingleCondition.OPERATION_EQUAL);
				assertEquals(f.getType(), "string");
				break;
			case "name":
				assertEquals(condition.getOper(), SingleCondition.OPERATION_LIKE);
				assertEquals(f.getType(), "string");
				break;
			case "time":
				assertEquals(condition.getOper(), SingleCondition.OPERATION_BETWEEN);
				assertEquals(f.getType(), "time");
				break;
			default:
				fail("ERROR name:"+name);
				break;
			}
		}
	}
	
	public static void assertSimpleFields(Field[] fields) {
		for(Field f : fields) {
			SingleCondition<?> condition = (SingleCondition<?>)f.getCondition();
			String name = f.getName();
			switch (name) {
			case "id":
				assertEquals(condition.getValue(), SIMPLE_TEST_ID);
				assertEquals(f.getType(), "string");
				break;
			case "name":
				assertEquals(condition.getValue(), SIMPLE_TEST_NAME);
				assertEquals(f.getType(), "string");
				break;
			case "time":
				assertEquals(condition.getValue(), SIMPLE_TEST_DATES);
				assertEquals(f.getType(), "time");
				break;
			default:
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		initFinders();
	}

	@BeforeAll
	public static void initFinders() {
		log.info("Init Finders.");
		initSampleFinder(SIMPLE_FINDER_ID, SIMPLE_FINDER_QL);
		initSampleFinder(TEST_ORDER_FINDER_ID, TEST_ORDER_FINDER_QL);
		
		initNoConfigFinder(TEST_ALL_TYPE_FINDER_ID, TEST_ALL_TYPE_FINDER_QL);
		
		initTestValueFinder();
	}
	
	protected static void initTestValueFinder() {
		log.info("Init TEST_VALUE Finder id[{}]: '{}'.", TEST_VALUE_FINDER_ID, TEST_VALUE_FINDER_QL);
		FinderBuilder finder = new FinderBuilder(TEST_VALUE_FINDER_ID, TEST_VALUE_FINDER_QL);
		FieldBuilder selectBtn = new FieldBuilder("select_checkbox", "view");
		selectBtn.setView(new ViewBuilder<String>(
				"input", 
				ValueBuilders.createTextValueBuilder("${id}"), 
				null,
				Collections.singletonMap("type", "checkbox")));
		FieldBuilder id = new FieldBuilder("id", "string");
		id.setCondition(SingleConditionBuilder.createLikeInstance("id"));
		FieldBuilder delBtn = new FieldBuilder("delete_btn", "view");
		delBtn.setView(new ViewBuilder<String>("button", ValueBuilders.createTextValueBuilder("${id}"), ValueBuilders.createTextValueBuilder("DELETE")) );
		finder.setFields(new FieldBuilder[] {selectBtn, id, delBtn});
		FinderManager mgr = new FinderManager();
		mgr.saveFinder(finder);
	}
	
	protected static void initSampleFinder(String id, String ql) {
		log.info("Init Finder id[{}]: '{}'.", id, ql);
		FinderBuilder finder = new FinderBuilder();
		finder.setId(id);
		FieldBuilder idField = new FieldBuilder();
		FieldBuilder nameField = new FieldBuilder();
		FieldBuilder timeField = new FieldBuilder();
		idField.setName("id");
		idField.setType("string");
		idField.setCondition(SingleConditionBuilder.createEqualInstance("id"));
		nameField.setName("name");
		nameField.setType("string");
		nameField.setCondition(SingleConditionBuilder.createLikeInstance("name"));
		timeField.setName("time");
		timeField.setType("time");
		timeField.setCondition(SingleConditionBuilder.createBetweenInstance("time"));
		finder.setFields(new FieldBuilder[] {idField, nameField, timeField});
		FinderOptions opt = new FinderOptions();
		opt.setDatabase("h2");
		finder.setOptions(opt);
		finder.setQl(ql);
		FinderManager mgr = new FinderManager();
		mgr.saveFinder(finder);
	}
	
	protected static void initNoConfigFinder(String id, String ql) {
		log.info("Init Empty Finder id[{}]: '{}'.", id, ql);
		FinderBuilder finder = new FinderBuilder();
		finder.setId(id);
		finder.setQl(ql);
		FinderManager mgr = new FinderManager();
		mgr.saveFinder(finder);
	}
}
