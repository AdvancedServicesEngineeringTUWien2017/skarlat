package at.ac.tuwien.infosys.access;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;

/**
 * Created by lenaskarlat on 6/14/17.
 */
public class TopicListener extends AWSIotTopic {

    public TopicListener(String topic, AWSIotQos qos) {
        super(topic, qos);
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        System.out.println(System.currentTimeMillis() + ": <<< " + message.getStringPayload());
    }

}
