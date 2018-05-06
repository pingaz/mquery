/**
 * 
 */
package pnp.mquery;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * @author ping
 *
 */
@RunWith(JUnitPlatform.class)
@SelectPackages({ "pnp.mquery.srv", "pnp.mquery.sboot", "pnp.mquery.sql", "pnp.mquery.db" })
public class AllTests {

}
