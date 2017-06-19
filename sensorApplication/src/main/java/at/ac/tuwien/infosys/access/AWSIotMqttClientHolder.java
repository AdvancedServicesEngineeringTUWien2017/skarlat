package at.ac.tuwien.infosys.access;

import com.amazonaws.services.iot.client.AWSIotMqttClient;
import org.springframework.stereotype.Service;

/**
 * Created by lenaskarlat on 6/16/17.
 */
@Service
public class AWSIotMqttClientHolder {
    private AWSIotMqttClient awsIotMqttClient;

    public AWSIotMqttClient getAwsIotMqttClient() {
        return awsIotMqttClient;
    }

    public void setAwsIotMqttClient(AWSIotMqttClient awsIotMqttClient) {
        this.awsIotMqttClient = awsIotMqttClient;
    }
}
