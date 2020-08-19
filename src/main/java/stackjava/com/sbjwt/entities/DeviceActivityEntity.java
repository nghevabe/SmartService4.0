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
    private int deviceid;

    @Column(name = "power_on")
    private boolean poweron;

    @Column(name = "power_value")
    private int powervalue;

    @Column(name = "light_color")
    private String lightcolor;

    @Column(name = "datetime_")
    private String datetime;

    public DeviceActivityEntity() {
    }

    public DeviceActivityEntity(int id, int deviceid, boolean poweron, int powervalue, String lightcolor, String datetime) {
        this.id = id;
        this.deviceid = deviceid;
        this.poweron = poweron;
        this.powervalue = powervalue;
        this.lightcolor = lightcolor;
        this.datetime = datetime;
    }

    public DeviceActivityEntity(int deviceid, boolean poweron, int powervalue, String lightcolor, String datetime) {
        this.deviceid = deviceid;
        this.poweron = poweron;
        this.powervalue = powervalue;
        this.lightcolor = lightcolor;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public boolean isPoweron() {
        return poweron;
    }

    public void setPoweron(boolean poweron) {
        this.poweron = poweron;
    }

    public int getPowervalue() {
        return powervalue;
    }

    public void setPowervalue(int powervalue) {
        this.powervalue = powervalue;
    }

    public String getLightcolor() {
        return lightcolor;
    }

    public void setLightcolor(String lightcolor) {
        this.lightcolor = lightcolor;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
