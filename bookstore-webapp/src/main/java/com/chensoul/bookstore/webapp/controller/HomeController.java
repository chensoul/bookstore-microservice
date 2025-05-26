package com.chensoul.bookstore.webapp.controller;

import com.chensoul.bookstore.webapp.service.WebHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(
            @RequestParam(name = "logoutSuccess", required = false) final Boolean logoutSuccess, final Model model) {
        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute(WebHelper.MSG_INFO, WebHelper.getMessage("authentication.logout.success"));
        }
        return "redirect:/products";
    }
}
