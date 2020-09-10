package io.fabric8.quickstarts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A bean that logs a message when you call the {@link #testEncryptedProperty()} method.
 * <p/>
 * Uses <tt>@Component("encryptedPropertiesBean")</tt> to register this bean with the name <tt>encryptedPropertiesBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("encryptedPropertiesBean")
public class EncryptedPropertiesBean {

    Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Value("${encrypted.password}")
    private String encryptedPassword;

    @Value("${unnencrypted.passord}")
    private String unencryptedPassword;

    public void testEncryptedProperty() {
        LOG.info("test properties decryption outside camel context: test.password        = {}", encryptedPassword);
        LOG.info("test unencrypted properties outside camel context: encrypted.property  = {}", unencryptedPassword);
    }
}
