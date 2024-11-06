package com.fmi.master.controllers;

import com.fmi.master.steriotypes.Controller;
import com.fmi.master.steriotypes.GetMapping;

@Controller(method = "GET", endpoint = "/home")
public class HomeController {

    @GetMapping("/home")
    public String index() {
        return "Home content";
    }
}
