package microservices;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.hawt.config.ConfigFacade;
import io.hawt.springboot.EnableHawtio;
import io.hawt.system.ConfigManager;
import io.hawt.web.AuthenticationFilter;

/**
 * @author jeff
 *
 */
@SpringBootApplication
@EnableHawtio
public class Application {
	@Autowired
	private ServletContext servletContext;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {
		System.setProperty(AuthenticationFilter.HAWTIO_AUTHENTICATION_ENABLED, "false");
		System.setProperty("hazelcast.jmx", "true");
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void init() {
		final ConfigManager configManager = new ConfigManager();
		configManager.init();
		servletContext.setAttribute("ConfigManager", configManager);
	}

	/**
	 * Set things up to be in offline mode
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public ConfigFacade configFacade() throws Exception {
		ConfigFacade config = new ConfigFacade() {
			public boolean isOffline() {
				return true;
			}
		};
		config.init();
		return config;
	}
}
