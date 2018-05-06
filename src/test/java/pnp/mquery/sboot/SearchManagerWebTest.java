/**
 * 
 */
package pnp.mquery.sboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import pnp.mquery.srv.DataInitialalTest;
import pnp.mquery.srv.SearcherList;
import pnp.mquery.srv.SearcherList.Item;

/**
 * @author ping
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootSearchApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SearchManagerWebTest {

	private static final Logger log = LogManager.getLogger(SearchManagerWebTest.class);

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testGetSearchList() {
		ResponseEntity<SearcherList> result = this.restTemplate.getForEntity("/searchs/list", SearcherList.class);
		log.info("Test GET Searcher list code is {}, Body:{}", result.getStatusCode(), result.getBody());
		assertEquals(result.getStatusCodeValue(), 200);
		SearcherList list = result.getBody();
		assertTrue(list.getItemList().size()>=3, "Searcher list item size should be more than 3, but was "+list.getItemList().size());
		boolean containSimple = false;
		boolean containOrder = false;
		boolean containAllType = false;
		for(Item item : list.getItemList()) {
			switch (item.getId()) {
			case DataInitialalTest.SIMPLE_FINDER_ID:
				containSimple = true;
				break;
			case DataInitialalTest.TEST_ALL_TYPE_FINDER_ID:
				containAllType = true;
				break;
			case DataInitialalTest.TEST_ORDER_FINDER_ID:
				containOrder = true;
				break;
			default:
				break;
			}
		}
		assertTrue(containSimple && containAllType && containOrder, "Should contains simple , order and all type.");
	}

}
