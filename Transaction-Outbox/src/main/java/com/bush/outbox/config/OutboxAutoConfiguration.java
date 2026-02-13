package com.bush.outbox.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Objects;

/**
 * Autoconfiguration class for Outbox Service
 */
@Configuration
@AutoConfigurationPackage(basePackages = "com.bush.outbox")
@ComponentScan(basePackages = "com.bush.outbox")
public class OutboxAutoConfiguration {
    @Autowired
    private Environment environment;

    /**
     * Autoconfiguring method for outbox transaction manager
     * <p>
     * Configuration is possible in two scenarios:
     * <ol>
     *     <li>Standard spring boot configuration</li>
     *     Used if the transaction manager bean defined by Spring Boot
     *     <li>Manual configuration</li>
     *     If for some reason manual configuration of the transaction manager is used,
     *     you must set {@code outbox.transaction-manager-name} declaration in application properties file
     * </ul>
     * @param beanFactory The factory is used to override the transaction manager
     * @return Platform transaction manager that suitable for outbox service purposes
     */
    @Bean(value = "outboxTransactionManager", defaultCandidate = false)
    @ConditionalOnMissingBean(name = "outboxTransactionManager")
    public PlatformTransactionManager outboxTransactionManager(BeanFactory beanFactory) {
        if (environment.containsProperty("outbox.transaction-manager-name")) {
            String transactionManagerName = Objects.requireNonNull(
                    environment.getProperty("outbox.transaction-manager-name"));
            return beanFactory.getBean(transactionManagerName, PlatformTransactionManager.class);
        }
        return beanFactory.getBean("transactionManager", PlatformTransactionManager.class);
    }
}
