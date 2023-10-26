package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.model.Customer;
import com.neobis.onlinestore.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getALlCustomers();
    }
    @PostMapping
    public ResponseEntity<String> saveCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateCustomer(@RequestParam String name,
                                                 @RequestParam String surname,
                                                 @PathVariable Long id) {
        return customerService.updateCustomer(name, surname, id);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id) {
        return customerService.deleteCustomerById(id);
    }
}
