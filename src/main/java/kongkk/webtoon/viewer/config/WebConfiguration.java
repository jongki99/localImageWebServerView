package kongkk.webtoon.viewer.config;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfiguration implements WebMvcConfigurer {
//	@Autowired
//	DataSource dataSource;

//	@Autowired
//	private ThymeleafProperties thymeleafProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new ThymeleafConfigurationInterceptor(thymeleafProperties)).order(2).addPathPatterns("/**/*.do");
    }
}
