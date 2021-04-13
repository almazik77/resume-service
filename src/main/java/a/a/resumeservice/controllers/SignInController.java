package a.a.resumeservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("mvc")
public class SignInController {
    @GetMapping("/signIn")
    public String getSignInPage(){
        return "sign_in";
    }
}
