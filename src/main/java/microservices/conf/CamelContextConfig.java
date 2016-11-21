package microservices.conf;

import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.messagehistory.MetricsMessageHistoryFactory;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelContextConfig {
	private static final String CAMEL_URL_MAPPING = "/camel/*";
	private static final String CAMEL_SERVLET_NAME = "CamelServlet";

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(),
				CAMEL_URL_MAPPING);
		registration.setName(CAMEL_SERVLET_NAME);
		return registration;
	}

	@Bean
	public CamelContextConfiguration contextConfiguration() {
		return new CamelContextConfiguration() {

			@Override
			public void beforeApplicationStart(CamelContext camelContext) {
				camelContext.setTracing(false);
				camelContext.addRoutePolicyFactory(new MetricsRoutePolicyFactory());
				// camelContext.setMessageHistoryFactory(new
				// MetricsMessageHistoryFactory());
			}

			@Override
			public void afterApplicationStart(CamelContext camelContext) {
			}
		};
	}
}
