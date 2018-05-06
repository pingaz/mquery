/**
 * 
 */
package pnp.mquery.sboot;

import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import pnp.mquery.srv.ResultTable;
import pnp.mquery.srv.Searcher;
import pnp.mquery.srv.SearcherList;
import pnp.mquery.srv.SearcherView;

/**
 * @author ping
 *
 */
@RestController
public class SearchController {

	@ApiOperation(value="连接成功", notes="测试连接")
	@GetMapping("/")
    public String connect() {
        return "Connect successful.";
    }
	@GetMapping("/index")
    public String getIndex() {
        return "<html>";
    }
	
	@GetMapping("/query/{id}")
	public ResultTable query(
			@PathVariable("id") String id, 
			@RequestParam(value="offset", required=false) Long offset,
			@RequestParam(value="size", required=false) Integer size,
			@RequestBody(required=false) Map<String, Object> query) {
		Searcher searcher = new Searcher();
		query = query == null || query.isEmpty() ? null : query;
		System.out.println("offset:"+offset+",size:"+size);
		return searcher.search(id, query, offset, size, LocaleContextHolder.getLocale());
	}
	
	@PostMapping("/finder")
	public boolean uploadFinder() {
		return false;
	}
	
	@GetMapping("/search/{id}")
	public SearcherView getView(
			@PathVariable("id") String id) {
		Searcher searcher = new Searcher();
		return searcher.getView(id);
	}
	
	@GetMapping("/searchs/list")
	public SearcherList getList() {
		Searcher searcher = new Searcher();
		return searcher.list();
	}
}
