package stackjava.com.sbjwt.controller;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.service.HouseDeviceService;

@RestController
@RequestMapping("/rest")
public class NodeMQTTController {

    @Autowired
    HouseDeviceService houseDeviceService;

    @CrossOrigin
    @RequestMapping(value = "/publish_signal/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> publishData(@PathVariable int id, @RequestParam String signal
            , @RequestParam(required=false) String lightColor, @RequestParam(required=false) String power) {

        if(lightColor==null){
            lightColor="none";
        }

        if(power==null){
            power="none";
        }

        HouseDeviceEntity houseDeviceEntity = houseDeviceService.findById(id);

        System.out.println("Topic: "+houseDeviceEntity.getTopic());
        System.out.println("type: "+houseDeviceEntity.getType());

        String topic = houseDeviceEntity.getTopic();
        String type = houseDeviceEntity.getType();
        String message = "";

        if(type.equals("Fan") || type.equals("Glass") || type.equals("Door")) {

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

        }

        if(type.equals("Light")){
            switch (signal) {
                case "ON":
                    // only me
                    if(id == 505) {
                        message = "2552552551";
                    }
                    if(id == 506){
                        message = "2552552552";
                    }
                    if(id == 507){
                        message = "2552552553";
                    }
                    // only me

//                    // for public
//                    message = "2552552551";
//                    // for public

                    break;
                case "OFF":
                    // only me
                    if(id == 505) {
                        message = "0000000001";
                    }
                    if(id == 506){
                        message = "0000000002";
                    }
                    if(id == 507){
                        message = "0000000003";
                    }
                    // only me

//                    // for public
//                    message = "0000000001";
//                    // for public
                    break;
                default:
                    return new ResponseEntity<String>("Invalid Signal", HttpStatus.CREATED);
            }
        }

            return sendMessage(message,topic);


    }

    public ResponseEntity<String> sendMessage(String message, String topic){

        //String topic        = "SMART_PROJECT/ESP_01";
        //String content      = mes;
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
            System.out.println("Publishing message: "+message);
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(qos);
            sampleClient.publish(topic, mqttMessage);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            return new ResponseEntity<String>("Publish", HttpStatus.CREATED);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("except "+me);
            me.printStackTrace();
            return new ResponseEntity<String>("Fail", HttpStatus.CREATED);
        }

    }

}
