package stackjava.com.sbjwt.controller;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class NodeMQTTController {

    @CrossOrigin
    @RequestMapping(value = "/publish_signal/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> publishData(@PathVariable int id, @RequestParam String signal) {

        String message = "";

        switch (signal) {
            case "ON":
                message = "ON0";
                break;
            case "OFF":
                message = "OFF0";
                break;
            default:
                return new ResponseEntity<String>("Invalid Signal", HttpStatus.CREATED);
        }

            return sendMessage(message);


    }

    public ResponseEntity<String> sendMessage(String mes){

        String topic        = "SMART_PROJECT/ESP_01";
        String content      = mes;
        int qos             = 0;
        String broker       = "tcp://tailor.cloudmqtt.com:11359";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("dhvuddfk");

            String password = "-2Y-VhjTn8lE";
            connOpts.setPassword(password.toCharArray());

            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            return new ResponseEntity<String>("Publish", HttpStatus.CREATED);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
            return new ResponseEntity<String>("Fail", HttpStatus.CREATED);
        }

    }

}
