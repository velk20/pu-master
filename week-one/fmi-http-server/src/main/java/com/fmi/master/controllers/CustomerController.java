package main.java.com.fmi.master.controllers;

import com.fmi.master.steriotypes.*;
import main.java.com.fmi.master.services.CustomerService;

@Controller(method = "GET", endpoint = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/nested")
    public String fetchNested() {
        return customerService.nested();
    }

    @GetMapping("/customer")
    public String fetchAllCustomers() {
        return customerService.hello();
    }

    @GetMapping("/customer/{id}")
    public String fetchSingleCustomers(@PathVariable("id") int id) {
        return "Customer single info - GET Request with id: " + id;
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
