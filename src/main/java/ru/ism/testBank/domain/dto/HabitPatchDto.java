package ru.ism.testBank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import ru.ism.testBank.annotation.oneNotNull.OneNotNull;
import ru.ism.testBank.domain.model.Period;

@Getter
@Builder
@OneNotNull(
        fields = {"title", "description", "period"},
        message = "Одно из значение не должно быть пустым должны быть введены")
@Schema(description = "Запрос на изменение привычки текущим пользователем")
public class HabitPatchDto {

    @Schema(description = "Название", example = "Пение")
    @Size(min = 3, max = 50, message = "Название привычки должно содержать от 3 до 50 символов")
    private String title;

    @Schema(description = "Описание привычки", example = "Пою в хоре вместе с друзьями")
    @Size(min = 5, max = 250, message = "Описание привычки должно содержать от 5 до 250 символов")
    private String description;

    @Schema(description = "Период", example = "DAILY или WEEKLY")
    private Period period;
}

