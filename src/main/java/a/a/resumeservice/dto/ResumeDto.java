package a.a.resumeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {
    private String id;
    private String name;
    private String about;
    private String workExperience;
    private String education;
    private String skills;
    private String references;

    private UserDto owner;
}
