package a.a.resumeservice.mappers;

import a.a.resumeservice.dto.ResumeDto;
import a.a.resumeservice.model.Resume;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResumeMapper {

    @Mapping(target = "owner.resumeList", ignore = true)
    ResumeDto map(Resume resume);

    @InheritInverseConfiguration
    Resume map(ResumeDto resumeDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateResumeFromDto(ResumeDto resumeDto, @MappingTarget Resume resume);
}
