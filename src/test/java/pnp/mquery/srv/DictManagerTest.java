/**
 * 
 */
package pnp.mquery.srv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author ping
 *
 */
class DictManagerTest {

	@Test
	void test() {
		DictManager dm = new DictManager();
		assertEquals("ID", dm.getString("test_all_type", "ID", Locale.CHINA));
		assertEquals("ID", dm.getString("test_all_type", "id", Locale.ENGLISH));

		assertEquals("Name", dm.getString("test_all_type", "NAME", Locale.CHINA));
		assertEquals("名字", dm.getString("NAME", Locale.CHINA));
		assertEquals("Name", dm.getString("test_all_type", "name", Locale.ENGLISH));

		assertEquals("美元", dm.getString("test_all_type", "USD", Locale.CHINA));
		assertEquals("USD", dm.getString("test_all_type", "usd", Locale.ENGLISH));

		assertEquals("价格", dm.getString("test_all_type", "PRICE", Locale.CHINA));
		assertEquals("Price", dm.getString("test_all_type", "price", Locale.ENGLISH));
	}

	@BeforeAll
	static void initDM() {
		DictManager dm = new DictManager();
		dm.saveString("id", "ID");
		dm.saveString("name", "Name");
		dm.saveString("name", "名字", Locale.CHINESE);
		dm.saveString("test_all_type", "usd", "USD");
		dm.saveString("test_all_type", "usd", "美元", Locale.CHINESE);
		dm.saveString("test_all_type", "price", "Price");
		dm.saveString("test_all_type", "price", "价格", Locale.CHINESE);
		dm.saveString("test_all_type", "cny", "CNY");
		dm.saveString("test_all_type", "cny", "人民币", Locale.CHINESE);
		dm.saveString("test_all_type", "size", "Size");
		dm.saveString("test_all_type", "size", "尺码", Locale.CHINESE);
		dm.saveString("test_all_type", "length", "Length");
		dm.saveString("test_all_type", "length", "长度", Locale.CHINESE);
		dm.saveString("test_all_type", "date", "Date");
		dm.saveString("test_all_type", "date", "日期", Locale.CHINESE);
		dm.saveString("test_all_type", "time", "Time");
		dm.saveString("test_all_type", "time", "时间", Locale.CHINESE);
		dm.saveString("test_all_type", "timestamp", "Timestamp");
		dm.saveString("test_all_type", "timestamp", "时间戳", Locale.CHINESE);
	}
}
