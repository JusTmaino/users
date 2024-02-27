package fr.supralog.users.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration for database tests
 * we can use @ComponentScan in case we have embedded jar that contains useful components and configurations
 * we can use @EnableTransactionManagement to make transactions work in Spring.
 * By default, spring activate it because we are using spring-data (also when using spring-tx)
 */
@Configuration
@EnableJpaRepositories(basePackages = "fr.supralog.users")
@EntityScan(basePackages = "fr.supralog.users.entities")
public class DatabaseTestConfiguration {
}
