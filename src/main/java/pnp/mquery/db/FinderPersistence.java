/**
 * 
 */
package pnp.mquery.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.JsonNode;

import pnp.mquery.srv.FinderBuilder;
import pnp.mquery.srv.JsonDeserializer;
import pnp.mquery.srv.SearcherList;

/**
 * @author ping
 *
 */
public class FinderPersistence {
	
	/**
	 * 
	 */
	private static final String DATAS_FINDERS_BASE_FOLDER = "datas/finders/";

	private static final Map<String, FinderBuilder> cacheFinders = new ConcurrentHashMap<>();
	
	private static final JsonPersistence json = new JsonPersistence(DATAS_FINDERS_BASE_FOLDER);

	public void save(FinderBuilder finder) {
		json.save(finder.getId(), finder);
		cacheFinders.put(finder.getId(), finder);
	}
	
	public void saveCache(FinderBuilder finder) {
		cacheFinders.put(finder.getId(), finder);
	}
	
	public FinderBuilder load(String id, boolean cache) {
		FinderBuilder builder = cache ? cacheFinders.get(id) : null;
		if(builder==null) {
			JsonNode node = json.load(id);
			builder = JsonDeserializer.deserializeFinderBuilder(node);
		}
		return builder;
	}
	
	public FinderBuilder load(String id) {
		return load(id, true);
	}
	
	public SearcherList list() {
		SearcherList list = new SearcherList();
		list.setItemList(loadAllItmes());
		return list;
	}

	private List<SearcherList.Item> loadAllItmes() {
		File folder = new File(DATAS_FINDERS_BASE_FOLDER);
		String[] children = folder.list();
		ArrayList<SearcherList.Item> items = new ArrayList<>();
		for(String f : children) {
			File file = new File(folder, f);
			if(file.isFile()) {
				FinderBuilder b = load(f);
				SearcherList.Item item =  new SearcherList.Item(f, b.getOptions());
				items.add(item);
			}
		}
		return items;
	}
	
	public static void clearCache() {
		cacheFinders.clear();
	}
}
