package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    public ResponseEntity<Customer> getAllCustomers() {
        return null;
    }

    public ResponseEntity<Customer> getCustomerById(long id) {
        return null;
    }

    public ResponseEntity<Customer> createCustomer(Customer customer) {
        return null;
    }

    public ResponseEntity<Customer> updateCustomer(Long id, Customer customer) {
        return null;
    }

    public ResponseEntity<Void> deleteCustomer(Long id) {
        return null;
    }
}
