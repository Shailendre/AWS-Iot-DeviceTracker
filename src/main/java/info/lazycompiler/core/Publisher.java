package info.lazycompiler.core;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import info.lazycompiler.model.TraceableMessage;
import info.lazycompiler.util.Ip2GeoTracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component("publisher")
public class Publisher {

    private final String topic          = System.getProperty("aws_location_topic");
    private static final Logger LOGGER  = Logger.getLogger(Publisher.class.getSimpleName());

    @Autowired
    private IotClient iotClient;

    @Autowired
    private Ip2GeoTracer ip2GeoTracer;

    /**
     * Main publisher method
     */
    public void publish() {
        AWSIotMqttClient mqttClient = iotClient.getMqttClient();
        if (mqttClient != null) {
            try {
                /**
                 *  get the response from the http client containing locations etc
                 *
                 * @see info.lazycompiler.model.TraceableMessage
                 */
                String response  = ip2GeoTracer.getResponse();
                if (response != null) {

                    // connect to the aws iot cloud
                    mqttClient.connect();

                    // create the message type as traceable message
                    TraceableMessage message = new TraceableMessage(topic, AWSIotQos.QOS0, response);

                    // publish to the topic
                    mqttClient.publish(message, 3000);
                }

            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage());
            } catch (AWSIotException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }

}
