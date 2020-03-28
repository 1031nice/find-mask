package me.donghun.findmask;

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

    public ArrayList<String> getAddress(String addr) throws IOException {
        URL url  = new URL("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=1");
        InputStream inputStream = url.openStream();
        String jsonText = readAll(inputStream);
        JSONObject json = new JSONObject(jsonText);
        int totalPages = (int) json.get("totalPages");
        MyArrayList<String> ret = new MyArrayList<>();
        for(int i=1; i<=1; i++){
            String urlString = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=" + i;
            URL each_url = new URL(urlString);
            inputStream = each_url.openStream();
            jsonText = readAll(inputStream);
            json = new JSONObject(jsonText);
            JSONArray storeInfos = (JSONArray) json.get("storeInfos");
            for(int j=0; j < storeInfos.length(); j++){
                JSONObject jsonObject = storeInfos.getJSONObject(j);
                String _addr = (String) jsonObject.get("addr");
                if(_addr.split(" ")[0].contains(addr))
                    ret.add(_addr);
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

}
