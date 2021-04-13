package a.a.resumeservice.services;

import a.a.resumeservice.dto.ResumeDto;
import a.a.resumeservice.mappers.ResumeMapper;
import a.a.resumeservice.model.Resume;
import a.a.resumeservice.model.User;
import a.a.resumeservice.repositories.ResumeRepository;
import a.a.resumeservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;
    private final UserRepository userRepository;

    @Override
    public ResumeDto save(String ownerId, ResumeDto resumeDto) {
        Optional<User> ownerCandidate = userRepository.findById(ownerId);
        if (ownerCandidate.isPresent()) {
            Resume resume = resumeMapper.map(resumeDto);
            resume.setOwner(ownerCandidate.get());
            resumeRepository.save(resume);
            return resumeMapper.map(resume);
        }
        throw new RuntimeException("No user with id " + ownerId);
    }

    @Override
    public ResumeDto getById(String resumeId) {
        Optional<Resume> resumeCandidate = resumeRepository.findById(resumeId);
        if (resumeCandidate.isPresent()) {
            return resumeMapper.map(resumeCandidate.get());
        }
        throw new IllegalArgumentException("No resume with id " + resumeId);
    }

    @Override
    public void deleteById(String userId, String resumeId) {
        Optional<Resume> resumeCandidate = resumeRepository.findById(resumeId);
        if (resumeCandidate.isPresent()) {
            if (resumeCandidate.get().getOwner().getId().equals(userId)) {
                resumeRepository.deleteById(resumeId);
            }
        }
    }

    @Override
    public ResumeDto update(String userId, String resumeId, ResumeDto resumeDto) {
        Optional<Resume> resumeCandidate = resumeRepository.findById(resumeId);
        if (resumeCandidate.isPresent()) {
            if (resumeCandidate.get().getOwner().getId().equals(userId)) {
                Resume resume = resumeCandidate.get();
                resumeMapper.updateResumeFromDto(resumeDto, resume);
                resumeRepository.save(resume);
                return resumeMapper.map(resume);
            }
            throw new RuntimeException("Not owner");
        }
        throw new IllegalArgumentException("No resume with id " + resumeId);
    }

    @Override
    public List<ResumeDto> findAll() {
        return resumeRepository.findAll().stream().map(resumeMapper::map).collect(Collectors.toList());
    }
}
