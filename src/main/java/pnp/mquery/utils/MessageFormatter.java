/**
 * 
 */
package pnp.mquery.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ping
 *
 */
public class MessageFormatter {

	private final MessageBuilder builder;
	
	public MessageFormatter(String format) {
		this.builder = new MessageBuilder(format);
	}
	
	public String format(Map<String, Object> values) {
		return builder.build(values);
	}
	
	class MessageBuilder{
		Map<String, MessageBuilder> fragements = new HashMap<>();
		MessageFragement first;
		public MessageBuilder(String format) {
			this.first = new MessageFragement();
			this.first.prepare(format);
		}
		
		public String build(Map<String, Object> values) {
			return first.build(new StringBuffer(), values).toString();
		}
	}
	class MessageFragement{
		String key;
		String pre;
		MessageFragement next;
		
		MessageFragement(){
		}

		void prepare(String unbuildMessage) {
			char[] charArray = unbuildMessage.toCharArray();
			int length = charArray.length;
			int nameIndex = -1;
			for(int i=0;i<length;i++) {
				char c = charArray[i];
				if(nameIndex!=-1 && (  c== '}')) {
					String name = new String(Arrays.copyOfRange(charArray, nameIndex, i));
					String postSql = unbuildMessage.substring(i+1);
					this.key = name;
					prepareNext(postSql);
					return;
				}else if(pre==null && c=='$' && i<length-2 && charArray[i+1]=='{') {
					pre = unbuildMessage.substring(0, i);
					nameIndex = i+2;
				}
			}
			pre = unbuildMessage;
		}
		void prepareNext(String post) {
			if(post.length() > 0) {
				next = new MessageFragement();
				next.prepare(post);
			}
		}
		
		StringBuffer build(StringBuffer message, Map<String, Object> keyValues) {
			message.append(pre);
			if(this.key!=null){
				message.append(keyValues.get(this.key));
			}
			if(next != null){
				next.build(message, keyValues);
			}
			return message;
		}
	}
}
