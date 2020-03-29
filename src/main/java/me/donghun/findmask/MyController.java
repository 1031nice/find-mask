package me.donghun.findmask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

@Controller
public class MyController {

    @Autowired
    MyService myService;


    @GetMapping("/page/{pageNum}")
    @ResponseBody
    public String findByPage(@PathVariable("pageNum") int pageNum) throws IOException {
          JSONArray page = myService.getPage(pageNum);
          return page.toString();
    }

    @GetMapping("/address")
    public String findByAddress(@RequestParam("addr") String addr, Model model) throws IOException {
        JSONArray addresses = myService.getAddress(addr);
        model.addAttribute("addresses", addresses);
        return "list";
    }

    @PostMapping("/code")
    @ResponseBody
    public String showLocationByCode(@RequestParam String lng, @RequestParam String lat) {
        String ID = "brig18d08p";
        String PW = "BjXKCJOwOn8BmyyrPaa07l9CRMg9jf6fpRqGPqtK";
        String coords = lng + "," + lat;
        HttpURLConnection conn = null;
        JSONObject responseJson = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc");
            sb.append("?coords=" + coords);
            sb.append("&output=json");
            sb.append("&request=coordsToaddr");
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
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                System.out.println(sb.toString());
                return sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}