package com.fmi.master.controllers;

import com.fmi.master.steriotypes.Controller;
import com.fmi.master.steriotypes.GetMapping;

@Controller(method = "GET", endpoint = "/customer")
public class CustomerController {

    @GetMapping("/customer")
    public String index() {
        return "Customer info";
    }
}
