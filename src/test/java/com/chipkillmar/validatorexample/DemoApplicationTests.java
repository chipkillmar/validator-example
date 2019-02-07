package com.chipkillmar.validatorexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ClockProvider;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    @Qualifier("localValidatorFactoryBean")
    private Validator validator;

    @MockBean
    private ClockProvider clockProvider;

    @Test
    public void contextLoads() {
        // Empty.
    }

    @Test
    public void testValidTimeDto() {
        Instant fixedTime = Instant.parse("2019-01-31T00:00:00Z");

        // For testing the clock, return a fixed time.
        when(clockProvider.getClock()).thenReturn(new Clock() {
            @Override
            public ZoneId getZone() {
                return ZoneId.of("UTC");
            }

            @Override
            public Clock withZone(ZoneId zone) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Instant instant() {
                return fixedTime;
            }
        });

        // Valid: time is within tolerance.
        TimeDto timeDto = new TimeDto(Instant.parse("2019-01-31T00:05:00Z"));
        Set<ConstraintViolation<TimeDto>> constraintViolations = validator.validate(timeDto);
        assertThat(constraintViolations).isEmpty();

        // Invalid: time is outside of tolerance.
        timeDto = new TimeDto(Instant.parse("2019-01-31T00:05:01Z"));
        constraintViolations = validator.validate(timeDto);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("must be a date in the past or in the present");
    }
}
