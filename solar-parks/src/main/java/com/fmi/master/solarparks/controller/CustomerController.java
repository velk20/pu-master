package com.fmi.master.solarparks.controller;

import com.fmi.master.solarparks.http.AppResponse;
import com.fmi.master.solarparks.model.Customer;
import com.fmi.master.solarparks.service.CustomerService;
import com.fmi.master.solarparks.util.DtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final DtoMapper dtoMapper;

    public CustomerController(CustomerService customerService, DtoMapper dtoMapper) {
        this.customerService = customerService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    @ResponseBody
    private ResponseEntity<?> getCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();

        return AppResponse.success()
                .withData(dtoMapper.convertCustomer(allCustomers))
                .build();
    }

    @GetMapping("/{id}")
    @ResponseBody
    private ResponseEntity<?> getCustomerById(@PathVariable long id) {
        Customer customerById = customerService.getCustomerById(id);

        return AppResponse.success()
                .withData(dtoMapper.convertCustomer(customerById))
                .build();
    }

    @PostMapping
    @ResponseBody
    private ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.createCustomer(customer);

        return AppResponse.created()
                .withMessage("Customer created successfully")
                .withData(dtoMapper.convertCustomer(newCustomer))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer customerResponseEntity = customerService.updateCustomer(id, customer);

        return AppResponse.success()
                .withData(dtoMapper.convertCustomer(customerResponseEntity))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return AppResponse.deleted()
                .withMessage("Customer deleted successfully")
                .build();
    }
}
