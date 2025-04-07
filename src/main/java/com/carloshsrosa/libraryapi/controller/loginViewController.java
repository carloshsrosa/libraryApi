package com.carloshsrosa.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginViewController {

    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }
}
