package microservices.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
public class RestBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		

	}

}
