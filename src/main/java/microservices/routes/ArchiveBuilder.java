package microservices.routes;

import org.apache.camel.builder.RouteBuilder;

public class ArchiveBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// @formatter:off
		
		//
		restConfiguration()
			.component("servlet");
		
		//
		rest("/tar")
			.post()
				.to("direct:tar");

		//
		from("direct:tar")
			.routeId("tarRoute")
			.unmarshal().gzip();

	}

}
