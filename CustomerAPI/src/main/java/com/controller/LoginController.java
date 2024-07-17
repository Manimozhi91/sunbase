package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Login;
import com.service.LoginService;

@Controller
public class LoginController {
	 @Autowired
	    private LoginService loginService;
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        Login user = loginService.validateLogin(username, password);
        if (user != null) {
            return "redirect:/customers"; 
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; 
        }
    }
}
