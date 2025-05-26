package com.chensoul.bookstore.webapp.controller;

import com.chensoul.bookstore.webapp.domain.AuthenticationRequest;
import com.chensoul.bookstore.webapp.service.WebHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login(
            @RequestParam(name = "loginRequired", required = false) final Boolean loginRequired,
            @RequestParam(name = "loginError", required = false) final Boolean loginError,
            final Model model) {
        model.addAttribute("authentication", new AuthenticationRequest());
        if (loginRequired == Boolean.TRUE) {
            model.addAttribute(WebHelper.MSG_INFO, WebHelper.getMessage("authentication.login.required"));
        }
        if (loginError == Boolean.TRUE) {
            model.addAttribute(WebHelper.MSG_ERROR, WebHelper.getMessage("authentication.login.error"));
        }
        return "authentication/login";
    }
}
