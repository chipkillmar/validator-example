# validator-example

A Spring Boot 2 application that demonstrates how to customize the Hibernate Validator.  

Specifically, this example configures a `ClockProvider`, and sets the
`hibernate.validator.temporal_validation_tolerance` global property.

This allows for a margin of error, due to out of sync clocks for example,  when validating temporal 
constraints such as:

    javax.validation.constraints.Past
    javax.validation.constraints.PastOrPresent
    javax.validation.constraints.Future
    javax.validation.constraints.FutureOrPresent

## Running the tests

This example also demonstrates how to mock a `ClockProvider` during unit testing.  Use the following
command line to execute the tests:

    ./gradlew clean test 

## References

1. [Hibernate Validator: 9.2.5. ClockProvider and temporal validation tolerance](
https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-clock-provider")

2. [Spring Framework Reference: Configuring a Bean Validation Provider](
https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#validation-beanvalidation-spring-other)

3. [Spring Framework LocalValidatorFactoryBean Javadoc](
https://docs.spring.io/spring-framework/docs/5.1.4.RELEASE/javadoc-api/org/springframework/validation/beanvalidation/LocalValidatorFactoryBean.html)