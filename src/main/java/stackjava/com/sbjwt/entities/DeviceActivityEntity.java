package stackjava.com.sbjwt.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "roles", "authorities" })
@Entity
@Table(name = "device_activity")
public class DeviceActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "device_id")
    private int deviceId;

    @Column(name = "power_on")
    private String powerOn;

    @Column(name = "power_value")
    private String powerValue;

    @Column(name = "light_color")
    private String lightColor;

    @Column(name = "datetime_")
    private String dateTime;

    public DeviceActivityEntity() {
    }

    public DeviceActivityEntity(int id, int deviceId, String powerOn, String powerValue, String lightColor, String dateTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.powerOn = powerOn;
        this.powerValue = powerValue;
        this.lightColor = lightColor;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getPowerOn() {
        return powerOn;
    }

    public void setPowerOn(String powerOn) {
        this.powerOn = powerOn;
    }

    public String getPowerValue() {
        return powerValue;
    }

    public void setPowerValue(String powerValue) {
        this.powerValue = powerValue;
    }

    public String getLightColor() {
        return lightColor;
    }

    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
