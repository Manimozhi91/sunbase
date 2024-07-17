package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Customer;
import com.service.RemoteAPIService;

@RestController
public class RemoteAPIController {
	@Autowired
	RemoteAPIService remoteService;
	
	@GetMapping(value="sync",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Customer>  getAllCustomers()  {
		return remoteService.getAllCustomers();
		
	}

}
