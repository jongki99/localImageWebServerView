package kongkk.webtoon.viewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class WebtoonViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebtoonViewerApplication.class, args);
	}

}
