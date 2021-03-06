package io.fabric8.quickstarts;

import org.apache.camel.component.properties.PropertiesParser;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.ClassPathResource;


@SpringBootApplication
public class EncryptedPropertiesSpringBootApplication {

    private static final String ENCRYPTION_ALGORITHM = "PBEWITHHMACSHA256ANDAES_256";

    private static final String MASTER_PASSWORD_ENV_VARIABLE_NAME = "JASYPT_ENCRYPTION_PASSWORD";

    private static final String SECURE_RANDOM_ALGORITHM = "NativePRNG";

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(EncryptedPropertiesSpringBootApplication.class, args);
    }

    @Bean
    public EnvironmentStringPBEConfig environmentVariablesConfiguration() {
        EnvironmentStringPBEConfig environmentStringPBEConfig = new EnvironmentStringPBEConfig();
        environmentStringPBEConfig.setAlgorithm(ENCRYPTION_ALGORITHM);
        environmentStringPBEConfig.setPasswordEnvName(MASTER_PASSWORD_ENV_VARIABLE_NAME);
        // Set the IVGenerator only if an initialization vector is used during encryption
        environmentStringPBEConfig.setIvGenerator(new RandomIvGenerator(SECURE_RANDOM_ALGORITHM));
        environmentStringPBEConfig.setSaltGenerator(new RandomSaltGenerator(SECURE_RANDOM_ALGORITHM));
        return environmentStringPBEConfig;
    }

    @Bean
    public StringEncryptor configurationEncryptor(EnvironmentStringPBEConfig environmentVariablesConfiguration) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setConfig(environmentVariablesConfiguration);
        return standardPBEStringEncryptor;
    }

    @Bean
    public static EncryptablePropertyConfigurer propertyConfigurer(StringEncryptor stringEncryptor) {
        EncryptablePropertyConfigurer propertyConfigurer =  new EncryptablePropertyConfigurer(stringEncryptor);
        propertyConfigurer.setLocations(new ClassPathResource("application.properties"));
        return propertyConfigurer;
    }

    @Bean
    @Primary
    public PropertiesParser propertyParser(PropertyResolver resolver, StringEncryptor stringEncryptor) {
        return new EncryptedPropertiesSpringParser(resolver, stringEncryptor);
    }

}
