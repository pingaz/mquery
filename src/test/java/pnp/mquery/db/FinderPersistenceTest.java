/**
 * 
 */
package pnp.mquery.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import pnp.mquery.srv.DataInitialalTest;
import pnp.mquery.srv.FieldBuilder;
import pnp.mquery.srv.FinderBuilder;
import pnp.mquery.srv.ValueBuilders;
import pnp.mquery.srv.ViewBuilder;

/**
 * @author ping
 *
 */
class FinderPersistenceTest extends DataInitialalTest{
	
	private static final Logger log = LogManager.getLogger(FinderPersistenceTest.class);

	@Test
	void testLoad() {
		FinderPersistence p = new FinderPersistence();
		FinderBuilder builder = p.load(SIMPLE_FINDER_ID);
		assertEquals(builder.getId(), SIMPLE_FINDER_ID);
		assertSimpleFieldBuilders(builder.getFields());
		log.info("Simple Finder was: {}", builder);
	}
	@Test
	void testLoadNoCache() {
		FinderPersistence p = new FinderPersistence();
		FinderBuilder builder = p.load(SIMPLE_FINDER_ID, false);
		assertEquals(builder.getId(), SIMPLE_FINDER_ID);
		assertSimpleFieldBuilders(builder.getFields());
		log.info("Simple Finder was: {}", builder);
	}
	
	@Test
	void testLoadValueRender() {
		FinderPersistence p = new FinderPersistence();
		FinderBuilder builder = p.load(TEST_VALUE_FINDER_ID, false);
		for(FieldBuilder f: builder.getFields()) {
			if(f.getName().equals("delete_btn")) {
				ViewBuilder<?> vb = f.getView();
				assertEquals("button", vb.getType());
				assertEquals(ValueBuilders.TextValueBuilder.class, vb.getValue().getClass());
				assertEquals(ValueBuilders.TextValueBuilder.class, vb.getText().getClass());
			}
		}
	}

}
