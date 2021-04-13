package a.a.resumeservice.services;

import a.a.resumeservice.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    ResumeDto save(String ownerId, ResumeDto resumeDto);

    ResumeDto getById(String resumeId);

    void deleteById(String userId, String resumeId);

    ResumeDto update(String userId, String resumeId, ResumeDto resumeDto);

    List<ResumeDto> findAll();
}
