package es.microforum.servicefrontend;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableJpaRepositories
@EnableTransactionManagement
@ImportResource("classpath:application-context.xml")
class ApplicationConfig {
}
