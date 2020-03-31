package me.donghun.findmask;
//
//import com.google.maps.GeoApiContext;
//import com.google.maps.GeocodingApi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

@Service
public class MyService {

    public JSONObject init() throws IOException {
        URL url  = new URL("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=1");
        InputStream inputStream = url.openStream();
        String jsonText = readAll(inputStream);
        JSONObject json = new JSONObject(jsonText);
        return json;
    }

    // page 번호와 key 값을 받은 뒤 해당 page에서 해당 key 값의 데이터를 JSONArray로 리턴
    private JSONArray getNaiveData(int pageNum, String key) throws IOException {
        String urlString = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=" + pageNum;
        URL url  = new URL(urlString);
        InputStream inputStream = url.openStream();
        String jsonText = readAll(inputStream);
        JSONObject json = new JSONObject(jsonText);
        JSONArray storeInfos = (JSONArray) json.get(key);
        return storeInfos;
    }

    public JSONArray findByAddress(String inputAddr) throws IOException {
        URL url  = new URL("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=1");
        InputStream inputStream = url.openStream();
        String jsonText = readAll(inputStream);
        JSONObject json = new JSONObject(jsonText);
        int totalPages = (int) json.get("totalPages");
        JSONArray ret = new JSONArray();
        for(int i=1; i<=totalPages; i++){
            JSONArray storeInfos = getNaiveData(i, "storeInfos");
            for(int j=0; j < storeInfos.length(); j++){
                JSONObject jsonObject = storeInfos.getJSONObject(j);
                String addr = (String) jsonObject.get("addr");
                MyLocation location = new MyLocation();
                if(addr.contains(inputAddr)) {
                    location.setAddr(addr);
                    location.setLat((double)jsonObject.get("lat"));
                    location.setLng((double)jsonObject.get("lng"));
                    location.setName((String)jsonObject.get("name"));
                    ret.put(location);
                }
            }
        }
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

    public Resource getImageByCoords(String lng, String lat) {
        String ID = "brig18d08p";
        String PW = "BjXKCJOwOn8BmyyrPaa07l9CRMg9jf6fpRqGPqtK";
        String coords = lng + "," + lat;
        HttpURLConnection conn = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("https://naveropenapi.apigw.ntruss.com/map-static/v2/raster");
            sb.append("?w=300&h=300");
            sb.append("&center=" + coords);
            sb.append("&level=16");
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", ID);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", PW);
            int responseCode = conn.getResponseCode();
            if (responseCode == 400) {
                System.out.println("400");
            } else if (responseCode == 401) {
                System.out.println("401");
            } else if (responseCode == 500) {
                System.out.println("500");
            } else { // 성공
                Resource resource = new InputStreamResource(conn.getInputStream());
                return resource;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
