package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Login;
import com.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	private LoginRepository loginRepo;
	
	public Login validateLogin(String username, String password) {
        return loginRepo.findByUsernameAndPassword(username, password);

	}

}
