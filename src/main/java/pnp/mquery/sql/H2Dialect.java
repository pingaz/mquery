/**
 * 
 */
package pnp.mquery.sql;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ping
 *
 */
public class H2Dialect implements Dialect {

	/* (non-Javadoc)
	 * @see pnp.mquery.sql.Dialect#getPageQuery(java.lang.String, java.lang.Long, int)
	 */
	@Override
	public String getPageQuery(String sql, Long offset, int size) {
		int limitIndex = StringUtils.lastIndexOfIgnoreCase(sql, "limit");
		if(limitIndex==-1) {
			return new StringBuilder(sql).append(" LIMIT ").append(offset).append(" ,").append(size).toString();
		}else {
			String preQl = sql.substring(0, limitIndex);
			char[] subQl = sql.substring(limitIndex+5).toCharArray();
			int endIndex = 0;
			int count = 0;
			StringBuilder limitQl = new StringBuilder();
			for(int i=0;i<subQl.length;i++) {
				char c = subQl[i];
				if(c!=' ' && c!=',') {
					endIndex = i;
					count = subQl.length - i;
					String limit = new String(subQl, 0, i);
					String[] offAndSize = limit.split(",");
					long oldOffset = Long.parseLong(offAndSize[0].trim());
					limitQl.append(" LIMIT ").append(oldOffset+offset);
					if(offAndSize.length==2) {
						int oldSize = Integer.parseInt(offAndSize[1].trim());
						oldSize = oldSize > size ? size : oldSize;
						limitQl.append(" ,").append(oldSize);
					}
					break;
				}
			}
			
			String postQl = new String(subQl, endIndex, count);
			String query = new StringBuilder(preQl).append(limitQl).append(postQl).toString();
			System.out.println("H2 Query:"+query);
			return query;
		}
	}

}
