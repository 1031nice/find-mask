package me.donghun.findmask;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
    @ResponseBody
    public String findByAddress(@PathVariable("addr") String addr) throws IOException {
        ArrayList<String> addresses = myService.getAddress(addr);
        return addresses.toString();
    }

}
