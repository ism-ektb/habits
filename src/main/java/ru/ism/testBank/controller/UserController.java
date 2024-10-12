package ru.ism.testBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ism.testBank.domain.dto.UserDto;
import ru.ism.testBank.domain.dto.UserPatchDto;
import ru.ism.testBank.domain.mapper.UserListMapper;
import ru.ism.testBank.domain.mapper.UserMapper;
import ru.ism.testBank.domain.model.User;
import ru.ism.testBank.service.UserService;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с учетной записью", description = "Доступен только авторизованным пользователям")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    private final UserListMapper listMapper;


    @GetMapping
    @Operation(summary = "Информация о текущем пользователе")
    public UserDto getInfo() {
        return mapper.modelToDto(service.getCurrentUser());
    }

    @PatchMapping
    @Operation(summary = "Изменение данных текущего пользователя")
    public UserDto patchUser(@Valid @RequestBody UserPatchDto userPatchDto){
        if (userPatchDto.getPassword() == null) {
            return mapper.modelToDto(service.patchUserInfo(userPatchDto));
        }
        User patch = mapper.dtoToModel(userPatchDto);
        return mapper.modelToDto(service.patchUserInfo(patch));
    }


    @DeleteMapping
    @Operation(summary = "удаление текущего пользователя")
    void deleteCurrentUser(){
        User user = service.getCurrentUser();
        service.deleteUser(user.getEmail());
    }
}