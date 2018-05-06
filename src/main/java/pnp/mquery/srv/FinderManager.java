/**
 * 
 */
package pnp.mquery.srv;

import java.util.Map;

import pnp.mquery.MQException;
import pnp.mquery.core.Query;
import pnp.mquery.core.Result;
import pnp.mquery.db.FinderPersistence;
import pnp.mquery.db.GlobalPersistence;

/**
 * @author ping
 *
 */
public class FinderManager {
	
	public Finder buildFinder(String id, Map<String, Object> fieldValues, Long offset, Integer size) {
		FinderBuilder builder = loadFinder(id);
		builder.setFields(fieldValues);
		Finder finder = builder.build();
		if(offset!=null)
			finder.setOffset(offset);
		if(size!=null)
			finder.setSize(size);
		return finder;
	}
	
	public Finder buildFinder(String id, Map<String, Object> fieldValues) {
		FinderBuilder builder = loadFinder(id);
		builder.setFields(fieldValues);
		return builder.build();
	}
	
	public Finder buildFinder(String id) {
		FinderBuilder builder = loadFinder(id);
		return builder.build();
	}

	public FinderBuilder loadFinder(String id) {
		FinderPersistence p = new FinderPersistence();
		FinderBuilder fb = p.load(id);
		if(fb.getFields() == null) {
			//build fields without configuration and save it into persistence.
			FinderOptions options = fb.getOptions() !=null ? 
					fb.getOptions() : GlobalPersistence.getInstance().getDefaultOptions();
			QueryManager qm = new QueryManager();
			Query query = qm.createQuery(options, fb.getQl(), 1);
			Result result = query.execute();
			fb.setFields(buildFieldBuilders(id, result));
			saveFinderInCache(fb);
		}
		return fb;
	}
	
	protected FieldBuilder[] buildFieldBuilders(String id, Result result) {
		if(result.next()) {
			String[] keys = result.getKeys();
			FieldBuilder[] fbs = new FieldBuilder[keys.length];
			for(int i=0;i<keys.length;i++) {
				String k = keys[i];
				Object v = result.getValue(k);
				System.out.println(k+":"+v+" - "+result.getValues());
				fbs[i] = FieldBuilder.createInstance(k, v.getClass());
			}
			return fbs;
		}else {
			throw new MQException("Error initialize finder fields, please check ql with finder id " + id);
		}
	}
	
	public void saveFinder(FinderBuilder finder) {
		FinderPersistence p = new FinderPersistence();
		p.save(finder);
	}
	
	public void saveFinderInCache(FinderBuilder finder) {
		FinderPersistence p = new FinderPersistence();
		p.saveCache(finder);
	}
}
