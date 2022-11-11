package kongkk.webtoon.viewer.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

//@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "${spring.application.base-package}")
@ConditionalOnProperty(name = "mps.config.database.default.enabled", havingValue = "true", matchIfMissing = false)
public class DatabaseConfig {
//    @Autowired
//    private AwsAdapter awsAdapter;
//
//    @Bean(name="dataSource")
//    @ConfigurationProperties("spring.datasource.hikari")
//    @Profile({"dev","stg","prd"})
//    public DataSource mysqlDataSource(@Value("${cloud.aws.secretsManager.dbSecretKey}")String secretName) {
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        try {
//
//            log.debug("secretName >> {}",secretName);
//            DbSecretValueDto dbSecret = awsAdapter.getDbSecretValue(secretName);
//
//            String url = "jdbc:mysql://"+dbSecret.getHost()+":"+dbSecret.getPort()+"/"+dbSecret.getDbname()  + "?statementInterceptors=brave.mysql.TracingStatementInterceptor";
//            log.info("DB URL : {}", url);
//
//            return DataSourceBuilder.create()
//                    .type(HikariDataSource.class)
//                    .url(url)
//                    .username(dbSecret.getUsername())
//                    .password(dbSecret.getPassword())
//                    .driverClassName("com.mysql.jdbc.Driver")
//                    .build();
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @Bean(name="dataSource")
//    @ConfigurationProperties("spring.datasource.hikari-local")
//    @ConditionalOnProperty(name = "mps.config.database.default.enabled", havingValue = "true", matchIfMissing = false)
//    @Profile("local")
    public DataSource mysqlDataSourceLocal() {

        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
}
