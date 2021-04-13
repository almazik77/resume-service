package a.a.resumeservice.controllers;

import a.a.resumeservice.dto.UserDto;
import a.a.resumeservice.security.details.UserDetailsImpl;
import a.a.resumeservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("mvc")
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getProfilePage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        String userId = userDetails.getUser().getId();
        return getProfileInfoById(userId, model);
    }

    @GetMapping("/{user-id}")
    @PreAuthorize("isAuthenticated()")
    public String getProfileInfoById(@PathVariable("user-id") String userId, Model model) {
        UserDto userDto = userService.getProfileInfo(userId);
        model.addAttribute("profileInfo", userDto);
        return "profile";
    }
}
