package at.ac.tuwien.infosys.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Created by lenaskarlat on 5/31/17.
 */


public interface SessionProxy {
    SensorSessionBean getSensorSessionBean();

}

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Service("sessionProxy")
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.INTERFACES)
class SessionProxyImpl implements SessionProxy {
    @Autowired
    private SensorSessionBean sensorSessionBean;


    @Override
    public SensorSessionBean getSensorSessionBean() {
        return sensorSessionBean;
    }


}
