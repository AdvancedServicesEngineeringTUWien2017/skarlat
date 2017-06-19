package at.ac.tuwien.infosys.access;

import at.ac.tuwien.infosys.session.SessionProxy;
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

    private final SessionProxy sessionProxy;
    private static final AWSIotQos TestTopicQos = AWSIotQos.QOS0;
    private static String[] topics;
    private static AWSIotMqttClientHolder awsIotClientHolder;

    @Autowired
    public AWSSubscribeAccess(AWSIotMqttClientHolder clientHolder, SessionProxy sessionProxy) {
        this.sessionProxy=sessionProxy;
        awsIotClientHolder = clientHolder;
        initClient();
        try {
            awsIotClientHolder.getAwsIotMqttClient().connect();
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
        String clientEndpoint = prop.getProperty("clientEndpoint");
        String clientId = prop.getProperty("clientId");
        topics = (prop.getProperty("topics")).split(",");
        String certificateFile = prop.getProperty("certificateFile");
        String privateKeyFile = prop.getProperty("privateKeyFile");
        if (awsIotClientHolder.getAwsIotMqttClient() == null && certificateFile != null && privateKeyFile != null) {
            String algorithm = ConnectionManager.getConfig("keyAlgorithm");
            ConnectionManager.KeyStorePasswordPair pair = ConnectionManager.getKeyStorePasswordPair(certificateFile, privateKeyFile, algorithm);
            awsIotClientHolder.setAwsIotMqttClient(new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword));
        }
        if (awsIotClientHolder.getAwsIotMqttClient() == null) {
            String awsAccessKeyId = ConnectionManager.getConfig("awsAccessKeyId");
            String awsSecretAccessKey = ConnectionManager.getConfig("awsSecretAccessKey");
            String sessionToken = ConnectionManager.getConfig("sessionToken");

            if (awsAccessKeyId != null && awsSecretAccessKey != null) {
                awsIotClientHolder.setAwsIotMqttClient(new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey,
                        sessionToken));
            }
        }
        if (awsIotClientHolder.getAwsIotMqttClient() == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
        }
    }

    public void subscribe(){
        AWSIotTopic topic = new TopicListener(topics[0], TestTopicQos,sessionProxy.getInfluxSessionBean().getInfluxAccess());
        try {
            awsIotClientHolder.getAwsIotMqttClient().subscribe(topic, false);
        } catch (AWSIotException e) {
            e.printStackTrace();
        }
    }
}

