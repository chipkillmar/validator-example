package com.chipkillmar.validatorexample;

import lombok.Data;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ClockProvider;
import javax.validation.Configuration;
import java.time.Clock;

/**
 * Spring Boot 2 application that demonstrates how to customize the Hibernate Validator.  Specifically, this example
 * configures a {@link ClockProvider}, and sets the {@code hibernate.validator.temporal_validation_tolerance} property,
 * which allows for a margin of error when validating temporal constraints such as
 * {@link javax.validation.constraints.Past}, {@link javax.validation.constraints.PastOrPresent},
 * {@link javax.validation.constraints.Future}, and {@link javax.validation.constraints.FutureOrPresent}.
 *
 * @see HibernateValidatorConfiguration#TEMPORAL_VALIDATION_TOLERANCE
 * @see <a href="https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-clock-provider">
 * 9.2.5. ClockProvider and temporal validation tolerance</a>
 */
@SpringBootApplication
@Data
public class DemoApplication {

    /**
     * Tolerance for temporal bean validation annotations such as {@link javax.validation.constraints.Past}.
     */
    @Value("${hibernate.validator.temporal_validation_tolerance:60000}")
    private int temporalValidationTolerance;

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean() {
            @Override
            protected void postProcessConfiguration(Configuration<?> configuration) {
                configuration.clockProvider(clockProvider());
                configuration.addProperty(HibernateValidatorConfiguration.TEMPORAL_VALIDATION_TOLERANCE,
                        String.valueOf(temporalValidationTolerance));
            }
        };
    }

    @Bean
    public ClockProvider clockProvider() {
        return Clock::systemUTC;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

