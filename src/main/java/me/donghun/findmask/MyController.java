package me.donghun.findmask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class MyController {

    @Autowired
    MyService myService;

    // ResponseBody로 JSONArray를 보내는건 안되는듯하다 JSONObject도 안되네 toString 붙여야되나
    @GetMapping("/test")
    @ResponseBody
    public String test() throws IOException {
       return myService.init().toString();
    }

    // n개씩 끊어서 m개의 페이지로 제공하고 싶은데 이건 담당자가 M이냐 V냐 C냐
    @GetMapping("/address")
    public String findByAddress(@RequestParam("addr") String addr, Model model) throws IOException {
        JSONArray addresses = myService.findByAddress(addr);
        model.addAttribute("addresses", addresses);
        return "list";
    }

    // 위도 경도를 이용해서 주소를 찾을 일이 없을듯. 어차피 공적 데이터에 위도 경도 주소 다 나오므로
//    @PostMapping("/code")
//    @ResponseBody
//    public String showLocationByCode(@RequestParam String lng, @RequestParam String lat) {
//        String ID = "brig18d08p";
//        String PW = "BjXKCJOwOn8BmyyrPaa07l9CRMg9jf6fpRqGPqtK";
//        String coords = lng + "," + lat;
//        HttpURLConnection conn = null;
//        JSONObject responseJson = null;
//        try {
//            StringBuilder sb = new StringBuilder();
//            sb.append("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc");
//            sb.append("?coords=" + coords);
//            sb.append("&output=json");
//            sb.append("&request=coordsToaddr");
//            URL url = new URL(sb.toString());
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", ID);
//            conn.setRequestProperty("X-NCP-APIGW-API-KEY", PW);
//            int responseCode = conn.getResponseCode();
//            if (responseCode == 400) {
//                System.out.println("400");
//            } else if (responseCode == 401) {
//                System.out.println("401");
//            } else if (responseCode == 500) {
//                System.out.println("500");
//            } else { // 성공
//                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                sb = new StringBuilder();
//                String line = "";
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                }
//                System.out.println(sb.toString());
//                return sb.toString();
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @PostMapping("/image")
    @ResponseBody
    public ResponseEntity<Resource> showImageByCoords(@RequestParam String lng, @RequestParam String lat) {
        Resource resource = myService.getImageByCoords(lng, lat);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

}