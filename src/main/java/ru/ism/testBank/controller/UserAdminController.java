package ru.ism.testBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ism.testBank.domain.dto.UserDto;
import ru.ism.testBank.domain.mapper.UserListMapper;
import ru.ism.testBank.domain.mapper.UserMapper;
import ru.ism.testBank.service.UserService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
@Tag(name = "Работа с учетной записью", description = "Доступен только администраторам")
public class UserAdminController {

    private final UserService service;
    private final UserMapper mapper;
    private final UserListMapper listMapper;


    @GetMapping
    @Operation(summary = "Список всех пользователей")
    public List<UserDto> getInfo() {
        return listMapper.modelsToDtos(service.getUsers());
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "удаление пользователя по email")
    public void deleteUser(@PathVariable String email){
        service.deleteUser(email);
    }

}
