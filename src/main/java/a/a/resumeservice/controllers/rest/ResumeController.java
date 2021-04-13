package a.a.resumeservice.controllers.rest;

import a.a.resumeservice.dto.ResumeDto;
import a.a.resumeservice.security.details.UserDetailsImpl;
import a.a.resumeservice.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Profile("rest")
@RequestMapping("/rest/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PreAuthorize("isAuthenticated()")
    public List<ResumeDto> getResumeList() {
        return resumeService.findAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResumeDto getResumeById(@PathVariable("id") String resumeId) {
        return resumeService.getById(resumeId);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResumeById(@PathVariable("id") String resumeId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return resumeService.deleteById(userDetails.getUser().getId(), resumeId) ?
                ResponseEntity.ok().body("") :
                ResponseEntity.badRequest().body("No resume");
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResumeDto updateResumeById(@PathVariable("id") String resumeId,
                                      @RequestBody ResumeDto resumeDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return resumeService.update(userDetails.getUser().getId(), resumeId, resumeDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResumeDto uploadResume(@RequestBody ResumeDto resumeDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return resumeService.save(userDetails.getUser().getId(), resumeDto);
    }
}
