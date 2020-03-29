package me.donghun.findmask;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;

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

    @GetMapping("/address/{addr}")
    public String findByAddress(@PathVariable("addr") String addr, Model model) throws IOException {
        JSONArray addresses = myService.getAddress(addr);
        model.addAttribute("addresses", addresses);
        return "list";
    }

    @PostMapping("/code")
    @ResponseBody
    public String showLocationByCode(@RequestParam String lat, @RequestParam String lng) {
        return "lat: " + lat + " lng: " + lng;
    }

}