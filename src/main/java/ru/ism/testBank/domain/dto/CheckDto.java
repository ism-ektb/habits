package ru.ism.testBank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@Schema(description = "Запрос на сохранение информации о выполнении привычки")
public class CheckDto {

    @Schema(description = "Id привычки")
    @Positive
    Long habitId;
    @Schema(description = "дата выполения привычки")
    @PastOrPresent(message = "Нельзя использовать будущую дату")
    LocalDate date;
}
