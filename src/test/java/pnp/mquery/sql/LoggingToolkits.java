/**
 * 
 */
package pnp.mquery.sql;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

/**
 * @author ping
 *
 */
public class LoggingToolkits {

	public static final void setRootLevel(Level level) {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	    Configuration conf = ctx.getConfiguration();
	    conf.getLoggerConfig(LogManager.ROOT_LOGGER_NAME).setLevel(Level.ALL);
	    ctx.updateLoggers(conf);
	}
	
	public static final void setLevel(Class<?> clazz, Level level) {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
	    Configuration conf = ctx.getConfiguration();
	    conf.getLoggerConfig(clazz.getName()).setLevel(Level.ALL);
	    ctx.updateLoggers(conf);
	}
}
