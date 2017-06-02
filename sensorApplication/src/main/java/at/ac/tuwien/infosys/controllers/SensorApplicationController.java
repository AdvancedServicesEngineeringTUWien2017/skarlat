package at.ac.tuwien.infosys.controllers;


import at.ac.tuwien.infosys.ModelAttributes;
import at.ac.tuwien.infosys.SensorApplication;
import at.ac.tuwien.infosys.Utils;
import at.ac.tuwien.infosys.entities.DataStore;
import at.ac.tuwien.infosys.entities.SensorDescription;
import at.ac.tuwien.infosys.exceptions.AccessException;
import at.ac.tuwien.infosys.session.SessionProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static at.ac.tuwien.infosys.controllers.MainNavController.SENSORS_URL;
import static at.ac.tuwien.infosys.controllers.MainNavController.redirectWithError;


/**
 * Created by lenaskarlat on 4/10/17.
 */
@Controller
@RequestMapping(SENSORS_URL)
public class SensorApplicationController {
    private static final String RECEIVE_URL = "/receive";
    private static final String ID = "/{id}";

    DataStore dataStoreInstance = DataStore.getInstance();

    private final SessionProxy sessionProxy;

    @Autowired
    public SensorApplicationController(SessionProxy sessionProxy) {
        this.sessionProxy = sessionProxy;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String acceptData(@RequestParam("sensorId") String sensorId, @RequestParam("sensorName") String sensorName, @RequestParam("time") String time, @RequestParam("data") String data,  Model model, RedirectAttributes redirectAttributes) throws Exception {
        //Utils.makeFrameMatrix(data);
        dataStoreInstance.putSensorDataFrame(sensorId, time, data);
        model.addAttribute(ModelAttributes.OBJECTS, dataStoreInstance.getAllSensors());
        return "sensors";
    }

    @RequestMapping(value = ID, method = RequestMethod.GET)
    public String showDataFrames(@PathVariable String id, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws Exception {
        ModelAttributes.initForSensors(model);
        return get_start(id, model, redirectAttributes);
    }

    private String get_start(String id, Model model, RedirectAttributes redirectAttributes) {
        try {
            initSensor(model, id);
            return "sensor";
        } catch (AccessException ex) {
            return redirectWithError(redirectAttributes, MainNavController.INDEX_URL, "Error while fetching business object", ex);
        }
    }

    protected void initSensor(Model model, String id) throws AccessException {
        sessionProxy.getSensorSessionBean().init(dataStoreInstance.getSensorById(id), model);
    }


    protected SensorDescription getWorkingSensor() {
        return sessionProxy.getSensorSessionBean().getWorkingSensor();
    }


}
