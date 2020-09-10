package io.fabric8.quickstarts;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

import static org.jasypt.properties.PropertyValueEncryptionUtils.decrypt;
import static org.jasypt.properties.PropertyValueEncryptionUtils.isEncryptedValue;


public class EncryptablePropertyConfigurer extends PropertyPlaceholderConfigurer  {


    private StringEncryptor stringEncryptor;
    @Autowired
    public EncryptablePropertyConfigurer(StringEncryptor stringEncryptor){
        this.stringEncryptor = stringEncryptor;

    }

    @Override
    protected Properties mergeProperties() throws IOException {
        final Properties mergedProperties = super.mergeProperties();
        convertProperties(mergedProperties);
        return mergedProperties;
    }

    @Override
    protected void convertProperties(final Properties props) {
        for(String key :props.stringPropertyNames()){
            props.put(key,convertPropertyValue(props.getProperty(key)));
        }
    }

    @Override
    protected String convertPropertyValue(final String originalValue) {
        if (isEncryptedValue(originalValue)) {
            return decrypt(originalValue, this.stringEncryptor);
        }
        return originalValue;
    }
}