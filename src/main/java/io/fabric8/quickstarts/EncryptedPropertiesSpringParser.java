package io.fabric8.quickstarts;

import java.util.Properties;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.core.env.PropertyResolver;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.apache.camel.component.properties.DefaultPropertiesParser;


public class EncryptedPropertiesSpringParser extends DefaultPropertiesParser {

    private PropertyResolver propertyResolver;

    private StringEncryptor stringEncryptor;

    EncryptedPropertiesSpringParser(PropertyResolver propertyResolver, StringEncryptor stringEncryptor) {
        this.propertyResolver = propertyResolver;
        this.stringEncryptor = stringEncryptor;
    }

    public String parseProperty(String key, String value, Properties properties) {

        String originalValue = this.propertyResolver.getProperty(key);

        if (!PropertyValueEncryptionUtils.isEncryptedValue(originalValue)) {
            return originalValue;
        } else {
            return PropertyValueEncryptionUtils.decrypt(originalValue, this.stringEncryptor);
        }
    }
}