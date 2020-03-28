package me.donghun.findmask;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class MyController {

    @Autowired
    MyService myService;

    @GetMapping("/show/{pageNum}")
    public ModelAndView action(@PathVariable("pageNum") int pageNum) throws IOException {
        ModelAndView mv = new ModelAndView();
        JSONArray page = myService.getPage(pageNum);
        mv.addObject(page);
        mv.setViewName("list");
        return mv;
    }

}
