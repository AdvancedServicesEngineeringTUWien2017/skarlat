package at.ac.tuwien.infosys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenaskarlat on 4/19/17.
 */
public class Utils {

    public static String transformLongToDate(String longTimeStamp) {
        long time = Long.parseLong(longTimeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
        return sdf.format(time);
    }


}