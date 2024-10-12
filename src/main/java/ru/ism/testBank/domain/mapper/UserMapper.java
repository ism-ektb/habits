package ru.ism.testBank.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ism.testBank.domain.dto.SignUpRequest;
import ru.ism.testBank.domain.dto.UserDto;
import ru.ism.testBank.domain.dto.UserPatchDto;
import ru.ism.testBank.domain.model.User;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User requestToModel(SignUpRequest signUpRequest);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User dtoToModel(UserPatchDto userPatchDto);

    UserDto modelToDto(User user);
}
