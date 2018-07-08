package info.lazycompiler.model;

import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;

public class MobilePhoneTopic extends AWSIotTopic {

    public MobilePhoneTopic(String topic, AWSIotQos qos) {
        super(topic, qos);
    }
}
