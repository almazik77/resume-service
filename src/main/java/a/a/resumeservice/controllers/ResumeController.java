package a.a.resumeservice.controllers;

import a.a.resumeservice.dto.ResumeDto;
import a.a.resumeservice.security.details.UserDetailsImpl;
import a.a.resumeservice.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;


    @GetMapping
    public String getResumeList(Model model) {
        model.addAttribute("resumeList", resumeService.findAll());
        return "list_resume";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String getCreateResumePage(Model model) {
        model.addAttribute("resumeDto", ResumeDto.builder().build());
        return "create_resume";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String uploadResume(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @ModelAttribute ResumeDto resumeDto,
                               Model model) {
        ResumeDto resultResumeDto = resumeService.save(userDetails.getUser().getId(), resumeDto);
        return "redirect:/resume/" + resultResumeDto.getId();
    }

    @GetMapping("/{resume-id}")
    @PreAuthorize("isAuthenticated()")
    public String getResumePage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @PathVariable("resume-id") String resumeId,
                                Model model) {
        ResumeDto resultResumeDto = resumeService.getById(resumeId);
        model.addAttribute("resumeDto", resultResumeDto);
        return "resume";
    }

    @DeleteMapping("/{resume-id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteResume(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PathVariable("resume-id") String resumeId) {
        resumeService.deleteById(userDetails.getUser().getId(), resumeId);
        return "redirect:/profile";
    }

    @PostMapping("/{resume-id}")
    @PreAuthorize("isAuthenticated()")
    public String updateResume(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PathVariable("resume-id") String resumeId,
                               @ModelAttribute ResumeDto resumeDto) {
        ResumeDto resultResumeDto = resumeService.update(userDetails.getUser().getId(), resumeId, resumeDto);
        return "redirect:/resume/" + resultResumeDto.getId();
    }
}
