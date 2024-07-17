package com.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.entity.Customer;
import com.repository.CustomerRepository;

@Service
public class CustomerService {
	private static final String REMOTE_API_URL = "http://localhost:9080/sync";
	@Autowired
	private CustomerRepository customerRepo;


	public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);

	}
	public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }
	public Customer updateCustomer(Long id, Customer customer) {
		  Customer existingCustomer = customerRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

	        existingCustomer.setFirstName(customer.getFirstName());
	        existingCustomer.setLastName(customer.getLastName());
	        existingCustomer.setStreet(customer.getStreet());
	        existingCustomer.setAddress(customer.getAddress());
	        existingCustomer.setCity(customer.getCity());
	        existingCustomer.setState(customer.getState());
	        existingCustomer.setEmail(customer.getEmail());
	        existingCustomer.setPhone(customer.getPhone());

	        return customerRepo.save(existingCustomer);
	    }
	public Customer getCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

	  public Customer updateCustomers(Long id, Customer customer) {
	        if (customerRepo.existsById(id)) {
	            customer.setId(id);
	            return customerRepo.save(customer);
	        }
	        return null;
	    }
	public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepo.findAll(pageable);
	}

	public Customer getCustomerById(Long id) {
		 return customerRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
	}

	public void deleteCustomer(Long id) {
		 Customer customer = customerRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

	        customerRepo.delete(customer);		
	        
	}

	public Page<Customer> searchCustomers(String keyword, Pageable pageable) {
        return customerRepo.findByFirstNameContaining(keyword, pageable);

	}
	//to communicate with RemoteAPI to get updated customer List
	 public List<Customer> fetchCustomersFromRemoteAPI() {
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<Customer[]> response = restTemplate.getForEntity(REMOTE_API_URL, Customer[].class);
	        Customer[] customersArray = response.getBody();

	        if (customersArray != null) {
	            return Arrays.asList(customersArray);
	        } else {
	            return Collections.emptyList();
	        }
	    }

}
