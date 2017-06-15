package at.ac.tuwien.infosys.access;

import at.ac.tuwien.infosys.entities.SensorDescription;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenaskarlat on 6/15/17.
 */
@Service
public class InfluxAccess {

    private InfluxDB influxDB;
    private String databaseName;

    @Autowired
    public InfluxAccess(@Value("${influxEndpoint}") String influxEndpoint, @Value("${databaseName}") String databaseName,@Value("${influxUser}") String influxUser, @Value("${influxPassword}") String influxPassword) {
       this.influxDB = InfluxDBFactory.connect(influxEndpoint, influxUser,influxPassword);
       this.databaseName = databaseName;
    }

    public List<SensorDescription> getAllSensors(){
        Query query = new Query("SELECT * FROM sensor", databaseName);
        QueryResult queryResult = influxDB.query(query);
        List<Result> results = queryResult.getResults();
        Series series = results.get(0).getSeries().get(0);
        List<List<Object>> values = series.getValues();
        List<String> columns = series.getColumns();
        List<SensorDescription> sensorDescriptionList = new ArrayList<>();
        for (List<Object> objectList:values) {
            String sensorName ="";
            String sensorType="";
            int x=0;
            int y=0;
            int z=0;
            for (int i=0; i<columns.size(); i++) {
                if (columns.get(i).equals("id"))  sensorName = objectList.get(i).toString();
                if (columns.get(i).equals("type")) sensorType = objectList.get(i).toString();
                if (columns.get(i).equals("x")) x= Integer.valueOf(objectList.get(i).toString());
                if (columns.get(i).equals("y")) y=Integer.valueOf(objectList.get(i).toString());
                if (columns.get(i).equals("z")) z=Integer.valueOf(objectList.get(i).toString());
            }
            SensorDescription sensor = new SensorDescription(false, sensorName,sensorType,x,y,z);
            sensorDescriptionList.add(sensor);
        }

        return sensorDescriptionList;
    }
}
