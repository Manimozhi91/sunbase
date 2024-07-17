package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Customer;
import com.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @GetMapping("/new")
    public String createNewCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "createCustomer";
    }
    @GetMapping("/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "editCustomer"; 
    }

    @PostMapping
    public String createCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.createCustomer(customer);
        return "redirect:/viewCustomers"; 
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute("customer") Customer customer) {
        customerService.updateCustomer(id, customer);
        return "redirect:/viewCustomers"; 
    }

    @GetMapping("/sync")
    public String syncCustomers() {
        List<Customer> remoteCustomers = customerService.fetchCustomersFromRemoteAPI();

        for (Customer remoteCustomer : remoteCustomers) {
            Customer existingCustomer = customerService.getCustomerByEmail(remoteCustomer.getEmail());

            if (existingCustomer != null) {
                existingCustomer.setFirstName(remoteCustomer.getFirstName());
                existingCustomer.setLastName(remoteCustomer.getLastName());
                existingCustomer.setStreet(remoteCustomer.getStreet());
                existingCustomer.setAddress(remoteCustomer.getAddress());
                existingCustomer.setCity(remoteCustomer.getCity());
                existingCustomer.setState(remoteCustomer.getState());
                existingCustomer.setPhone(remoteCustomer.getPhone());

                customerService.updateCustomers(existingCustomer.getId(), existingCustomer);
            } else {
                customerService.createCustomer(remoteCustomer);
            }
        }

        return "redirect:/customers"; 
    }

 
	@GetMapping
    public String getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = customerService.getAllCustomers(pageable);
        model.addAttribute("customers", customersPage);
        return "viewCustomers";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/viewCustomers";
    }

    @GetMapping("/search")
    public String searchCustomers(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = customerService.searchCustomers(keyword, pageable);
        model.addAttribute("customers", customersPage);
        model.addAttribute("keyword", keyword);
        return "viewCustomers";
    }
}
