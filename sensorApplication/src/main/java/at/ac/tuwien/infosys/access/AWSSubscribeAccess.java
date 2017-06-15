package at.ac.tuwien.infosys.access;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by lenaskarlat on 6/14/17.
 */
@Service
public class AWSSubscribeAccess {

    private static final AWSIotQos TestTopicQos = AWSIotQos.QOS0;
    private static String[] topics;

    private static AWSIotMqttClient awsIotClient;

    public static void setClient(AWSIotMqttClient client) {
        awsIotClient = client;
    }


    public AWSSubscribeAccess() {
        initClient();
        try {
            awsIotClient.connect();
            AWSIotTopic topic = new TopicListener(topics[0], TestTopicQos);
            awsIotClient.subscribe(topic, false);
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }

    private static void initClient() {
        Properties prop = new Properties();

        try (InputStream stream = new FileInputStream("src/main/resources/application.properties")) {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // String value = prop.getProperty(name);
        String clientEndpoint = prop.getProperty("clientEndpoint");
      //  String clientEndpoint = ConnectionManager.getConfig("clientEndpoint");
        String clientId = prop.getProperty("clientId");
        topics = (prop.getProperty("topics")).split(",");
        String certificateFile = prop.getProperty("certificateFile");
        String privateKeyFile = prop.getProperty("privateKeyFile");
        if (awsIotClient == null && certificateFile != null && privateKeyFile != null) {
            String algorithm = ConnectionManager.getConfig("keyAlgorithm");
            ConnectionManager.KeyStorePasswordPair pair = ConnectionManager.getKeyStorePasswordPair(certificateFile, privateKeyFile, algorithm);
            awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
        }
        if (awsIotClient == null) {
            String awsAccessKeyId = ConnectionManager.getConfig("awsAccessKeyId");
            String awsSecretAccessKey = ConnectionManager.getConfig("awsSecretAccessKey");
            String sessionToken = ConnectionManager.getConfig("sessionToken");

            if (awsAccessKeyId != null && awsSecretAccessKey != null) {
                awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey,
                        sessionToken);
            }
        }
        if (awsIotClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
        }
    }
}

