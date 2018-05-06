/**
 * 
 */
package pnp.mquery.srv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import pnp.mquery.TestLogger;


/**
 * @author ping
 *
 */
public class FinderManagerTests extends DataInitialalTest implements TestLogger {

    public static final Logger LOG = LogManager.getLogger(DataInitialalTest.class);

	@Test
	public void testCreateEmptyFinderBuilder() {
		FinderManager manager = new FinderManager();
		FinderBuilder finder = manager.loadFinder(SIMPLE_FINDER_ID);
		assertEquals(finder.getId(), SIMPLE_FINDER_ID);
		assertEquals(finder.getFields().length, 3);
		for(FieldBuilder f : finder.getFields()) {
			String name = f.getName();
			switch (name) {
			case "id":
				assertEquals(f.getCondition().build(), SingleCondition.createEqualInstance(name, null));
				break;
			case "name":
				assertEquals(f.getCondition().build(), SingleCondition.createLikeInstance(name, null));
				break;
			case "time":
				assertEquals(f.getCondition().build(), SingleCondition.createBetweenInstance(name, null));
				break;
			default:
				break;
			}
		}
	}

	@Test
	public void testSimpleFinder() {
		FinderManager manager = new FinderManager();
		Map<String, Object> map = createSimpleFieldValues();
		Finder finder = manager.buildFinder(SIMPLE_FINDER_ID, map);
		assertEquals(finder.getId(), SIMPLE_FINDER_ID);
		assertEquals(finder.getFields().length, 3);
		assertSimpleFields(finder.getFields());
	}

	@Test
	public void testNoCfgFinder() {
		FinderManager manager = new FinderManager();
		Finder finder = manager.buildFinder(TEST_ALL_TYPE_FINDER_ID);
		assertEquals(finder.getFields().length, 10);
		for(Field f : finder.getFields()) {
			String name = f.getName();
			switch (name) {
			case "date":
				assertEquals(f.getType(), "date");
				assertEquals(f.getCondition(), SingleCondition.createBetweenInstance(name, null));
				break;
			case "timestamp":
				assertEquals(f.getType(), "datetime");
				assertEquals(f.getCondition(), SingleCondition.createBetweenInstance(name, null));
				break;
			case "time":
				assertEquals(f.getType(), "time");
				assertEquals(f.getCondition(), SingleCondition.createBetweenInstance(name, null));
				break;
			default:
				break;
			}
		}
	}
}
