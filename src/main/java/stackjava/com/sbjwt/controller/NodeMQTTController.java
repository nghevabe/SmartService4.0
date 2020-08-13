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

            String message = createMessage(id, signal, lightColor, power);

            HouseDeviceEntity houseDeviceEntity = houseDeviceService.findById(id);
            String topic = houseDeviceEntity.getTopic();

            if(message.equals("fail"))
            {
                return new ResponseEntity<String>("Invalid Signal", HttpStatus.CREATED);
            }
            else {
                return sendMessage(message, topic);
            }

    }

    public String createMessage(int id, String signal, String lightColor, String power){

        HouseDeviceEntity houseDeviceEntity = houseDeviceService.findById(id);

        String type = houseDeviceEntity.getType();
        String message = "";
        //int lightValue = 255;
        int powerValue = 0;

        if(type.equals("Fan") || type.equals("Glass") || type.equals("Door")) {

            if (power != null) {

                powerValue = (int) (((float) 950 / 100) * Integer.parseInt(power));
                if (powerValue < 100) {
                    powerValue = 100;
                }
                
                String stringPowerValue = Integer.toString(powerValue);

                if (stringPowerValue.length() == 1) {
                    stringPowerValue = "00" + stringPowerValue;
                }

                if (stringPowerValue.length() == 2) {
                    stringPowerValue = "0" + stringPowerValue;
                }

                message = stringPowerValue ;

            } else {
                message = "475";
            }

            switch (signal) {
                case "ON":
                    message = message + "0";
                    break;
                case "OFF":
                    message = "OFF0";
                    break;
                default:
                    message = "fail";
            }
        }

        if(type.equals("Light")){

            if(power != null){
                powerValue = (int) (((float) 255/100) * Integer.parseInt(power));
                System.out.println("power: "+powerValue);
            }
            String stringLightValue = Integer.toString(powerValue);

            if(stringLightValue.length() == 1) {
                stringLightValue =  "00"+stringLightValue;
            }

            if(stringLightValue.length() == 2) {
                stringLightValue =  "0"+stringLightValue;
            }

            if(lightColor != null) {

                if (lightColor.equals("RED")) {
                    message = stringLightValue + "000000";
                }

                if (lightColor.equals("GREEN")) {
                    message = "000" +stringLightValue + "000";
                }

                if (lightColor.equals("BLUE")) {
                    message = "000000" + stringLightValue;
                }

                if (lightColor.equals("YELLOW")) {
                    message = stringLightValue + stringLightValue + "000";
                }

                if (lightColor.equals("VIOLET")) {
                    message =  stringLightValue + "000" + stringLightValue;
                }

                if (lightColor.equals("AQUA")) {
                    message = "000" + stringLightValue + stringLightValue;
                }

                if (lightColor.equals("WHITE")) {
                    message = stringLightValue + stringLightValue + stringLightValue;
                }
            } else {
                message = "255255255";
            }

            switch (signal) {
                case "ON":
                    // only me
                    if(id == 505) {
                        message = message+"1";
                    }
                    if(id == 506){
                        message = message+"2";
                    }
                    if(id == 507){
                        message = message+"3";
                    }
                    // only me

//                    // for public
//                    message = message+"1";
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
                    message = "fail";
            }
        }

        return message;
    }

    public ResponseEntity<String> sendMessage(String message, String topic){

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
