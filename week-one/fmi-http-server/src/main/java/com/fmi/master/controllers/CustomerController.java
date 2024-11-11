package com.fmi.master.controllers;

import com.fmi.master.steriotypes.*;

@Controller(method = "GET", endpoint = "/customer")
public class CustomerController {

    @GetMapping("/customer")
    public String fetchAllCustomers() {
        return "Customer info - GET Request";
    }

    @PostMapping("/customer")
    public String createNewCustomer() {
        return "Customer info - POST Request";
    }

    @PutMapping("/customer")
    public String updateCustomer() {
        return "Customer info - PUT Request";
    }

    @DeleteMapping("/customer")
    public String removeCustomer() {
        return "Customer info - DELETE Request";
    }
}
