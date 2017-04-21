package at.ac.tuwien.infosys;


import at.ac.tuwien.infosys.entities.DataStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Created by lenaskarlat on 4/10/17.
 */
@Controller
public class SensorApplicationController {
    private static final String RECEIVE_URL = "/receive";

    DataStore dataStoreInstance = DataStore.getInstance();

    @RequestMapping(value = RECEIVE_URL, method = RequestMethod.POST)
    public void acceptData(@RequestParam("time") String time, @RequestParam("data") String data) throws Exception {
        dataStoreInstance.putSensorDataFrame(time, data);
    }

}
