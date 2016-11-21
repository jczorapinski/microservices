package microservices.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hazelcast.HazelcastConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.hazelcast.collection.common.DataAwareItemEvent;

/**
 * @author jeff
 *
 */
@Component
@EnableConfigurationProperties
public class MessagingBuilder extends RouteBuilder {

	/**
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
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
					.process( new Processor() {
						
						@Override
						public void process(Exchange exchange) throws Exception {
							Object messageBody = exchange.getIn().getBody();
							DataAwareItemEvent event = (DataAwareItemEvent)messageBody;
							System.out.println(event.getItem());
						}
					})
				.when(header(HazelcastConstants.LISTENER_ACTION).isEqualTo(HazelcastConstants.REMOVED))
					.log(LoggingLevel.INFO, "Bye bye message -> ${body}")
				.otherwise()
					.log(LoggingLevel.WARN, "WTFJH :-/");
			

		// @formatter:on
	}

}
