package org.studiorailgun.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    
    @GetMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return "Hello " + name;
    }

    @GetMapping("/")
    @ResponseBody
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "login";
    }
    
}
