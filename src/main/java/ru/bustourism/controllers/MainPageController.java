package ru.bustourism.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping(path = "/")
    public String mainPage() {
        return "/pages/mainPage";
    }

}
