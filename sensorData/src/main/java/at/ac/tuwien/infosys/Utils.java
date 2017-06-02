package at.ac.tuwien.infosys;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by lenaskarlat on 4/18/17.
 */
public class Utils {

    //get all file paths from a folder to emulate emitting data
    public static List<String> getFilePathsInFolder(String sensorName) {
        List<String> pathList = new ArrayList<String>();
        try (Stream<Path> paths = Files.walk(Paths.get("../../ase_data/atc-rawdata-1/" + sensorName))) {//folder ase_data has to be stored near git repository
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    pathList.add(filePath.toString());
                }
            });
            Collections.sort(pathList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathList;
    }

    public static void sendRequest(String url, List<NameValuePair> nvpList) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nvpList));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            System.out.println(response.getStatusLine());
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}