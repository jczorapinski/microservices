package microservices.conf;

import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jeff
 *
 */
@Configuration
public class CamelContextConfig {
	/**
	 * 
	 */
	private static final String CAMEL_URL_MAPPING = "/microservices/*";
	/**
	 * 
	 */
	private static final String CAMEL_SERVLET_NAME = "CamelServlet";

	/**
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(),
				CAMEL_URL_MAPPING);
		registration.setName(CAMEL_SERVLET_NAME);
		return registration;
	}

	/**
	 * @return
	 */
	@Bean
	public CamelContextConfiguration contextConfiguration() {
		return new CamelContextConfiguration() {

			@Override
			public void beforeApplicationStart(CamelContext camelContext) {
				SpringCamelContext springCamelContext = (SpringCamelContext) camelContext;
				springCamelContext.setName("microservices_ctx");
				springCamelContext.setTracing(false);
				springCamelContext.addRoutePolicyFactory(new MetricsRoutePolicyFactory());
				// This is throwing and IllegalArgumentException for some
				// reason. Figure out WHY!??! I
				// springCamelContext.setMessageHistoryFactory(new
				// MetricsMessageHistoryFactory());
			}

			@Override
			public void afterApplicationStart(CamelContext camelContext) {
			}
		};
	}
}
