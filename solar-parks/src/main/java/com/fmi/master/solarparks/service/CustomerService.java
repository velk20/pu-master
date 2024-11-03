package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.exception.CustomerNotFoundException;
import com.fmi.master.solarparks.model.Customer;
import com.fmi.master.solarparks.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public ResponseEntity<Customer> getCustomerById(long id) {
        Customer customer = findCustomerOrThrow(id);
        return ResponseEntity.ok(customer);
    }

    public ResponseEntity<Customer> createCustomer(Customer customer) {
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatusCode.valueOf(201));
    }

    public ResponseEntity<Customer> updateCustomer(Long id, Customer newCustomer) {
        Customer customer = findCustomerOrThrow(id);
        customer.setName(newCustomer.getName())
                .setActive(newCustomer.isActive())
                .setNumberOfProjects(newCustomer.getNumberOfProjects());

        return ResponseEntity.ok(customerRepository.save(customer));
    }

    public ResponseEntity<Void> deleteCustomer(Long id) {
        Customer customer = findCustomerOrThrow(id);
        customerRepository.delete(customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Customer findCustomerOrThrow(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException("Customer not found!"));
    }
}
