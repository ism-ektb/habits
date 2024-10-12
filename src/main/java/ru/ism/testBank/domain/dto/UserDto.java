package ru.ism.testBank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class UserDto {

    @Schema(description = "Логин", example = "Вова")
    private String name;
    @Schema(description = "Email", example = "1@1.1")
    private String email;

}