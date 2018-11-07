package com.store.dev.webpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @RequestMapping("/test")
    public ModelAndView testJsp() {
        System.out.println("Test!Test!Test!");
        return new ModelAndView("content");
    }

}
