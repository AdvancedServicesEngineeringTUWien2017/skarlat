package at.ac.tuwien.infosys.entities;

import at.ac.tuwien.infosys.Utils;

import java.util.UUID;

/**
 * Created by lenaskarlat on 5/24/17.
 */
public class DataFrame {

    private String id;
    private String sensorId;
    private String timeStamp;

    private String data;
    private String[][] colors;


    public DataFrame() {
    }

    public DataFrame(String sensorId, String timeStamp, String data) {
        this.sensorId  = sensorId;
        this.timeStamp = timeStamp;
        this.data = data;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return Utils.transformLongToDate(timeStamp);
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String[][] getColors() {
        return colors;
    }

    public void setColors(String[][] colors) {
        this.colors = colors;
    }
}

