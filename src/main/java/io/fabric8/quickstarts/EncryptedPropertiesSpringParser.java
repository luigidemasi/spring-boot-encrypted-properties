package io.fabric8.quickstarts;

import org.apache.camel.component.properties.DefaultPropertiesParser;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;

import java.util.Properties;

import static org.jasypt.properties.PropertyValueEncryptionUtils.decrypt;
import static org.jasypt.properties.PropertyValueEncryptionUtils.isEncryptedValue;


public class EncryptedPropertiesSpringParser extends DefaultPropertiesParser {

    private PropertyResolver propertyResolver;

    private StringEncryptor stringEncryptor;

    @Autowired
    public EncryptedPropertiesSpringParser(PropertyResolver propertyResolver, StringEncryptor stringEncryptor) {
        this.propertyResolver = propertyResolver;
        this.stringEncryptor = stringEncryptor;
    }

    @Override
    public String parseProperty(String key, String value, Properties properties) {

        String originalValue = propertyResolver.getProperty(key);

        return isEncryptedValue(originalValue) ? decrypt(originalValue, stringEncryptor) : originalValue;

    }
}