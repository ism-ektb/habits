package ru.ism.testBank.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserOutDto {

        private UserDto user;
        private Long balance;

}
