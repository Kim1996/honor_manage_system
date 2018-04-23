package com.ting.honormanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {
    @RequestMapping(value = "/api/logout")
    public String userLogout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "/home.html";
    }
}