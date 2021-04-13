package a.a.resumeservice.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("mvc")
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String getHomePage() {
        return "home";
    }
}
