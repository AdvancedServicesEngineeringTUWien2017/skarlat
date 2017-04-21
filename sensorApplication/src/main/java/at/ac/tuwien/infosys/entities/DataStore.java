package at.ac.tuwien.infosys.entities;

import java.util.*;

/**
 * Created by lenaskarlat on 4/19/17.
 */
public class DataStore {

    private static DataStore instance = null;

    Map<String, String> sensorDataFrameMap = new HashMap();

    protected DataStore() {
    }

    public static DataStore getInstance() {
        if(instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public List<String> getTimeStampsList(){

        List timeStampsList = new ArrayList<>(sensorDataFrameMap.keySet());
        Collections.sort(timeStampsList);
        return timeStampsList;
    };

    public void putSensorDataFrame(String timeStamp, String data){
        sensorDataFrameMap.put(timeStamp,data);
    }

    public String getSensorDataFrame(String timeStamp){
        return sensorDataFrameMap.get(timeStamp);
    }

}