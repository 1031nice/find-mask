package me.donghun.findmask;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class MyService {

    public JSONArray init() throws IOException {
        URL url  = new URL("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=1");
        InputStream inputStream = url.openStream();
        String jsonText = readAll(inputStream);
        JSONObject json = new JSONObject(jsonText);
        JSONArray storeInfos = (JSONArray) json.get("storeInfos");
        return storeInfos;
    }

    public JSONArray getPage(int pageNum) throws IOException {
        String urlString = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=" + pageNum;
        URL url  = new URL(urlString);
        InputStream inputStream = url.openStream();
        String jsonText = readAll(inputStream);
        JSONObject json = new JSONObject(jsonText);
        JSONArray storeInfos = (JSONArray) json.get("storeInfos");
        return storeInfos;
    }

    public JSONArray getAddress(String inputAddr) throws IOException {
        URL url  = new URL("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=1");
        InputStream inputStream = url.openStream();
        String jsonText = readAll(inputStream);
        JSONObject json = new JSONObject(jsonText);
        int totalPages = (int) json.get("totalPages");
        JSONArray ret = new JSONArray();
        for(int i=1; i<=1; i++){
            String urlString = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=" + i;
            URL each_url = new URL(urlString);
            inputStream = each_url.openStream();
            jsonText = readAll(inputStream);
            json = new JSONObject(jsonText);
            System.out.println(json.toString());
            System.out.println();
            JSONArray storeInfos = (JSONArray) json.get("storeInfos");
            for(int j=0; j < storeInfos.length(); j++){
                JSONObject jsonObject = storeInfos.getJSONObject(j);
                String addr = (String) jsonObject.get("addr");
//                JSONObject newJsonObject = new JSONObject();
                MyLocation location = new MyLocation();
                if(addr.contains(inputAddr)) {
                    location.setAddr(addr);
                    location.setLat((double)jsonObject.get("lat"));
                    location.setLng((double)jsonObject.get("lng"));
                    location.setName((String)jsonObject.get("name"));
                    location.setCode((String)jsonObject.get("code"));
//                    newJsonObject.put("name", jsonObject.get("name"))
//                            .put("addr", addr)
//                            .put("lng", jsonObject.get("lng"))
//                            .put("lat", jsonObject.get("lat"));
//                    System.out.println(newJsonObject.toString());
//                    ret.put(newJsonObject);
                    ret.put(location);
                }
            }
        }
        System.out.println(ret.toString());
        return ret;
    }

    private String readAll(InputStream inputStream) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder stringBuilder = new StringBuilder();
            int cp;
            while((cp = bufferedReader.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            return stringBuilder.toString();
        } finally {
            inputStream.close();
        }
    }

}
