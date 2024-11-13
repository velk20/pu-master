package com.fmi.master.solarparks.controller;

import com.fmi.master.solarparks.http.AppResponse;
import com.fmi.master.solarparks.model.Customer;
import com.fmi.master.solarparks.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ResponseBody
    private ResponseEntity<?> getCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();

        return AppResponse.success()
                .withData(allCustomers)
                .build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    private ResponseEntity<?> getCustomerById(@PathVariable long id) {
        ResponseEntity<Customer> customerById = customerService.getCustomerById(id);

        return AppResponse.success()
                .withData(customerById.getBody())
                .build();
    }

    @PostMapping
    @ResponseBody
    private ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        ResponseEntity<Customer> newCustomer = customerService.createCustomer(customer);

        return AppResponse.success()
                .withMessage("Customer created successfully")
                .withData(newCustomer.getBody())
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        ResponseEntity<Customer> customerResponseEntity = customerService.updateCustomer(id, customer);

        return AppResponse.success()
                .withData(customerResponseEntity.getBody())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        return AppResponse.deleted().build();
    }
}
