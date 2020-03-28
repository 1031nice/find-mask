package me.donghun.findmask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Dictionary;
import java.util.Map;

@Component
public class MyRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        URL url  = new URL("https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=1");
        InputStream inputStream = url.openStream();
        String jsonText = readAll(inputStream);
        JSONObject json = new JSONObject(jsonText);
        int totalPages = (int) json.get("totalPages");
        for(int i=1; i<=totalPages; i++){
            String urlString = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/stores/json?page=" + i;
            URL each_url = new URL(urlString);
            inputStream = each_url.openStream();
            jsonText = readAll(inputStream);
            json = new JSONObject(jsonText);
            JSONArray storeInfos = (JSONArray) json.get("storeInfos");
            System.out.println(storeInfos.toString());
        }
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
