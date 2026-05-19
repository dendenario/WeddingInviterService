package io.denario.inviter.confuguration;

import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

@Configuration
@Profile("prod")
public class JpaConfig {

    @Bean
    public HibernatePropertiesCustomizer platformDialectCustomizer() {
        return (Map<String, Object> hibernateProperties) -> {
            // Заставляем Hibernate 6 использовать LIMIT/OFFSET вместо FETCH FIRST
            hibernateProperties.put("hibernate.query.limit_by_navigator_method", "true");
        };
    }
}
