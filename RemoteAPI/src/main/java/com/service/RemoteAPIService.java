package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Customer;
import com.repository.RemoteAPIRepository;


@Service
public class RemoteAPIService {
	@Autowired
	private RemoteAPIRepository remoteRepo;
	public List<Customer> getAllCustomers() {
        return remoteRepo.findAll();
    }


}
