/**
 * 
 */
package pnp.mquery.sboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ping
 *
 */
@SpringBootApplication
@EnableSwagger2
public class SpringBootSearchApplication {
	public static void main(String[] args) {
        SpringApplication.run(SpringBootSearchApplication.class, args);
    }
}
