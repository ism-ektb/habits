package ru.ism.testBank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.ism.testBank.domain.model.Period;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@Schema(description = "Информация о сохраненной привычке")
public class HabitOutDto {

    @Schema(description = "ID - привычки")
    private Long id;

    @Schema(description = "Пользователь привычки")
    private UserDto user;

    @Schema(description = "Название", example = "Пение")
    private String title;

    @Schema(description = "Описание привычки", example = "Пою в хоре вместе с друзьями")
    private String description;

    @Schema(description = "Период", example = "DAILY или WEEKLY")
    private Period period;

    @Schema(description = "дата создания привычки")
    private LocalDateTime start;
}

