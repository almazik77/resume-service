package a.a.resumeservice.repositories;

import a.a.resumeservice.model.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResumeRepository extends MongoRepository<Resume, String> {
}
