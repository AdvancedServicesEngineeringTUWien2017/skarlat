package at.ac.tuwien.infosys;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by lenaskarlat on 4/12/17.
 */
@RestController
@RequestMapping("/")
public class SensorDataController {

    private List<String> timeStamps = new ArrayList<>();

    private List<String> paths = Utils.getFilePathsInFolder("A2");
    private Iterator i = paths.iterator();

    @RequestMapping(value = "data", method = RequestMethod.POST)
    public void acceptData(@RequestParam("data") String data, @RequestParam("time") String time) throws Exception {
        timeStamps.add(time);
    }

    @RequestMapping(value = "data", method = RequestMethod.GET)
    public String acceptData() {
        String html = "<html><head></head><body style='background: white; color: black; font-family: Verdana'>" +
                "<h1>Sensor Status Page</h1>";
        html += "<p><b>Sensor A2</b></p>";
        html += "<hr/>";
        html += "<p>  Sent Data Time Stamps </p>";
        html += "<ol>";
        for (String timeStamp : timeStamps) {
            long time = Long.parseLong(timeStamp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
            html += "<li>" + sdf.format(time) + "</li>";
        }
        html += "</ol>";
        html += "</body></html>";
        return html;
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void sendData() {
        RawDataFrame rdf = new RawDataFrame();
        try {
            if (i.hasNext()) {
                String fileName = i.next().toString();
                rdf.readSingleFrame(fileName);
                System.out.println(fileName);
                String time = Objects.toString(rdf.time, null);
                String data = Arrays.toString(rdf.data);
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("time", time));
                nvps.add(new BasicNameValuePair("data", data));
                Utils.sendRequest("http://127.0.0.1:8080/receive", nvps);
                Utils.sendRequest("http://127.0.0.1:8081/data", nvps);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

