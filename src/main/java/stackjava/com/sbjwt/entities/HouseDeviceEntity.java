package stackjava.com.sbjwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "roles", "authorities" })
@Entity
@Table(name = "house_device")
public class HouseDeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "name_")
    private String name;

    @Column(name = "type_")
    private String type;

    @Column(name = "id_wifi")
    private String idWifi;

    @Column(name = "pass_wifi")
    private String passWifi;

    @Column(name = "topic")
    private String topic;

    @Column(name = "light_color")
    private String lightColor;

    @Column(name = "power_value")
    private String powerValue;

    @Column(name = "powerOn")
    private String powerOn;

    public HouseDeviceEntity() {
    }

    public HouseDeviceEntity(int id, int userId ,String name, String type, String idWifi, String passWifi, String topic,
                             String lightColor, String powerValue, String powerOn) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.idWifi = idWifi;
        this.passWifi = passWifi;
        this.topic = topic;
        this.lightColor = lightColor;
        this.powerValue = powerValue;
        this.powerOn = powerOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdWifi() {
        return idWifi;
    }

    public void setIdWifi(String idWifi) {
        this.idWifi = idWifi;
    }

    public String getPassWifi() {
        return passWifi;
    }

    public void setPassWifi(String passWifi) {
        this.passWifi = passWifi;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLightColor() {
        return lightColor;
    }

    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    public String getPowerValue() {
        return powerValue;
    }

    public void setPowerValue(String powerValue) {
        this.powerValue = powerValue;
    }

    public String getPowerOn() {
        return powerOn;
    }

    public void setPowerOn(String powerOn) {
        this.powerOn = powerOn;
    }
}
