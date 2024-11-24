package main.java.com.fmi.master.controllers;

import com.fmi.master.steriotypes.Autowired;
import com.fmi.master.steriotypes.Controller;
import com.fmi.master.steriotypes.GetMapping;
import main.java.com.fmi.master.services.HomeService;

@Controller(method = "GET", endpoint = "/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/home")
    public String index() {
        return homeService.getIndexHome();
    }

}
