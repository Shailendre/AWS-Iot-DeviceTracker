package info.lazycompiler.core;

import com.amazonaws.services.iot.client.AWSIotMqttClient;
import info.lazycompiler.util.GenerateKeyStore;
import org.springframework.stereotype.Component;

/**
 * This is aws iot client
 *
 *
 *
 *
 * @author shailendra.singh
 *
 */
@Component("iotClient")
public class IotClient {

    private final String awsBrokerAddress   = System.getProperty("aws_broker_address");
    private final String clientID           = System.getProperty("aws_client_id");
    private final String certificateFile    = System.getProperty("aws_certificate_id");
    private final String privateKeyFile     = System.getProperty("aws_private_key");

    private AWSIotMqttClient mqttClient;

    public IotClient() throws Exception{

        // generate the keystore pairs
        GenerateKeyStore.KeyStorePasswordPair keystore = GenerateKeyStore.getKeyStorePasswordPair(certificateFile, privateKeyFile);

        // create the mqtt client
        mqttClient =  new AWSIotMqttClient(awsBrokerAddress, clientID, keystore.keyStore, keystore.keyPassword);
    }

    public AWSIotMqttClient getMqttClient() {
        return mqttClient;
    }
}
