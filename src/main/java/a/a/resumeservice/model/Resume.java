package a.a.resumeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "resume")
public class Resume {
    @Id
    private String id;
    private String name;
    private String about;
    private String workExperience;
    private String education;
    private String skills;
    private String references;

    @DBRef
    private User owner;
}
