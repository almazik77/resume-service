package a.a.resumeservice.mappers;

import a.a.resumeservice.dto.UserDto;
import a.a.resumeservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {ResumeMapper.class})
public interface UserMapper {
    @Mapping(target = "hashPassword", ignore = true)
    User map(UserDto userDto);

    UserDto map(User user);
}
