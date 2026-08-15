package ru.bustourism.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainPageController {

    @GetMapping(path = "/")
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping(path = "/sessionclear")
    public String clearSession(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
