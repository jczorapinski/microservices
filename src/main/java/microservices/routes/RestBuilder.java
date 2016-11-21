package microservices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jeff
 *
 */
@Component
@EnableConfigurationProperties
public class RestBuilder extends RouteBuilder {

	/**
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {

	}

}
