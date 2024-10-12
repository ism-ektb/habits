package ru.ism.testBank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import ru.ism.testBank.domain.model.Period;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Запрос на сохранение привычки текущим пользователем")
public class HabitInDto {

    @Schema(description = "Название", example = "Пение")
    @Size(min = 3, max = 50, message = "Название привычки должно содержать от 3 до 50 символов")
    @NotBlank(message = "Название привычки не может быть пустыми")
    private String title;

    @Schema(description = "Описание привычки", example = "Пою в хоре вместе с друзьями")
    @Size(min = 5, max = 250, message = "Описание привычки должно содержать от 5 до 250 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String description;

    @Schema(description = "Период", example = "DAILY или WEEKLY")
    private Period period;
}
