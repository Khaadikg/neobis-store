package com.neobis.onlinestore.service;

import com.neobis.onlinestore.model.Customer;
import com.neobis.onlinestore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getALlCustomers() {
        return customerRepository.findAll();
    }
    public ResponseEntity<?> getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return ResponseEntity.badRequest().body("No such user found by id = " + id);
        }
        return ResponseEntity.ok().body(customer);
    }
    public ResponseEntity<String> saveCustomer(Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(
                    "Customer with username \"" + customer.getUsername()
                    + "\" already exist!");
        }
        customerRepository.save(customer);
        return ResponseEntity.ok("Customer saved fine!");
    }
    public ResponseEntity<String> updateCustomer(String name,  String surname, Long id) {
        Customer customer1 = customerRepository.findById(id).orElse(null);
        if (customer1 == null) {
            return ResponseEntity.badRequest().body("No such user found by id = " + id);
        }
        customer1.setName(name);
        customer1.setSurname(surname);
        customerRepository.save(customer1);
        return ResponseEntity.ok("Customer successfully saved!");
    }
    public ResponseEntity<String> deleteCustomerById(Long id) {
        if (customerRepository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("No such user found by id = " + id);
        }
        customerRepository.deleteById(id);
        return ResponseEntity.ok("Customer successfully deleted!");
    }
}
