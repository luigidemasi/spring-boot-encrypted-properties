package io.fabric8.quickstarts;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and logs decrypted properties.
 */
@Component
public class EncryptedPropertiesSpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {
        from("timer:hello?period={{timer.period}}&repeatCount=2")
                .routeId("encrypted-properties-route")
                .log("test properties decryption  inside camel context: encrypted.password   = {{encrypted.password}}")
                .log("test unencrypted properties inside camel context: unnencrypted.passord = {{unnencrypted.passord}}")
                .bean("encryptedPropertiesBean", "testEncryptedProperty")
                .to("mock:out");
    }
}
