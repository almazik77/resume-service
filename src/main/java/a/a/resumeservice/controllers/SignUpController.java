package a.a.resumeservice.controllers;

import a.a.resumeservice.dto.SignUpDto;
import a.a.resumeservice.dto.UserDto;
import a.a.resumeservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/signUp")
@RequiredArgsConstructor
public class SignUpController {
    @Autowired
    private final UserService userService;

    @GetMapping
    public String getSignUpPage() {
        return "sign_up";
    }

    @PostMapping
    public String signUp(@ModelAttribute @Valid SignUpDto signUpDto) {
        userService.save(signUpDto);
        return "redirect:/signIn";
    }
}
