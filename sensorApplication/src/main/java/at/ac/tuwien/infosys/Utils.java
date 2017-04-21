package at.ac.tuwien.infosys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenaskarlat on 4/19/17.
 */
public class Utils {

    public static List<String> transformLongToDate(List<String> longTimeStamps){
        List<String> timeStampsFormatted =  new ArrayList<>();
        for (String timeStamp:longTimeStamps){
            long time= Long.parseLong(timeStamp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
            timeStampsFormatted.add(sdf.format(time));
        }
        return timeStampsFormatted;
    }
}