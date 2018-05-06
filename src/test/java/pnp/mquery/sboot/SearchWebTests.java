/**
 * 
 */
package pnp.mquery.sboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import javax.swing.text.View;

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
import pnp.mquery.srv.ResultTable;
import pnp.mquery.srv.SearcherView;

/**
 * @author ping
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootSearchApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SearchWebTests {
	
	private static final Logger log = LogManager.getLogger(SearchWebTests.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void test() {
		String body = this.restTemplate.getForObject("/", String.class);
		log.info("Test Body:{}", body);
		assertThat(body).isEqualTo("Connect successful.");
	}

	@Test
	public void testGetSimpleView() {
		ResponseEntity<SearcherView> result = this.restTemplate.getForEntity("/search/"+DataInitialalTest.SIMPLE_FINDER_ID, SearcherView.class);
		log.info("Test GET SimpleView code is {}, Body:{}", result.getStatusCode(), result.getBody());
		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().getId(), DataInitialalTest.SIMPLE_FINDER_ID);
		assertEquals(result.getBody().getFields().length, 3);
	}

	@Test
	public void testGetTestOrderView() {
		ResponseEntity<SearcherView> result = this.restTemplate.getForEntity("/search/"+DataInitialalTest.TEST_ORDER_FINDER_ID, SearcherView.class);
		log.info("Test GET TestOrderView code is {}, Body:{}", result.getStatusCode(), result.getBody());
		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().getId(), DataInitialalTest.TEST_ORDER_FINDER_ID);
		assertEquals(result.getBody().getFields().length, 3);
	}
	
	@Test
	public void testSearchSimpleWithoutAny() {
		ResponseEntity<ResultTable> result = this.restTemplate.getForEntity("/query/"+DataInitialalTest.SIMPLE_FINDER_ID, ResultTable.class);
		log.info("Test GET SimpleView query result code is {}, Body:{}", result.getStatusCode(), result.getBody());
		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().size(), 50);
	}
	
	@Test
	public void testSearchAllTypeWithNoCfg() {
		ResponseEntity<ResultTable> result = this.restTemplate.getForEntity("/query/"+DataInitialalTest.TEST_ALL_TYPE_FINDER_ID, ResultTable.class);
		log.info("Test GET all type query result code is {}, Body:{}", result.getStatusCode(), result.getBody());
		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().size(), 20);
	}
	
	@Test
	public void testSearchValueRender() {
		ResponseEntity<ResultTable> result = this.restTemplate.getForEntity("/query/"+DataInitialalTest.TEST_VALUE_FINDER_ID, ResultTable.class);
		log.info("Test GET value render query result code is {}, Body:{}", result.getStatusCode(), result.getBody());
		assertEquals(result.getStatusCodeValue(), 200);
		assertEquals(result.getBody().size(), 20);
		
		ResultTable table = result.getBody();
		System.out.println("rows:"+table.getRows().get(0));
		assertEquals("DELETE", ((Map<String, Object>)table.getRows().get(0).get("delete_btn")).get("text"));
	}
}
