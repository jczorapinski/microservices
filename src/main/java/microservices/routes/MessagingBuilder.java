package microservices.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hazelcast.HazelcastConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
public class MessagingBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// @formatter:off

		/**
		 * 
		 */
		from("timer:queuePoller?period=60000")
			.routeId("timerIpcMessages")
			.setBody().constant("MESSSAGE: " + System.currentTimeMillis())
			.log(LoggingLevel.INFO, "GOT HERE")
			.to("hazelcast:queue:ipcMessages");

		/**
		 * 
		 */
		from("hazelcast:queue:ipcMessages")
			.routeId("consumerIpcMessages")
			.choice()
				.when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.ADDED))
					.log(LoggingLevel.INFO, "Got new message -> ${body}")
					.to("mock:added")
				.when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.REMOVED))
					.log(LoggingLevel.INFO, "Bye bye message -> ${body}")
					.to("mock:removed")
				.otherwise()
					.log(LoggingLevel.WARN, "WTFJH :-/");
			

		// @formatter:on
	}

}
