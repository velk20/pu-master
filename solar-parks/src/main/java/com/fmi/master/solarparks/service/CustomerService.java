package com.fmi.master.solarparks.service;

import com.fmi.master.solarparks.exception.CustomerNotFoundException;
import com.fmi.master.solarparks.model.Customer;
import com.fmi.master.solarparks.model.Project;
import com.fmi.master.solarparks.repository.CustomerRepository;
import com.fmi.master.solarparks.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ProjectRepository projectRepository;

    public CustomerService(CustomerRepository customerRepository, ProjectRepository projectRepository) {
        this.customerRepository = customerRepository;
        this.projectRepository = projectRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(long id) {
        return findCustomerOrThrow(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer newCustomer) {
        Customer customer = findCustomerOrThrow(id);
        customer.setName(newCustomer.getName())
                .setActive(newCustomer.isActive())
                .setNumberOfProjects(newCustomer.getNumberOfProjects());

        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = findCustomerOrThrow(id);
        List<Project> projects = projectRepository.findAllByCustomerId(id).orElse(new ArrayList<>());
        projects.forEach(project -> project.setCustomer(null));

        projectRepository.saveAll(projects);
        customerRepository.delete(customer);
    }

    private Customer findCustomerOrThrow(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id:%s not found!", id)));
    }

}
