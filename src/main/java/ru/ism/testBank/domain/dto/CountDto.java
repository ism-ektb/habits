package ru.ism.testBank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;


@Schema(description = "Подсчет текущих серий выполнения привычек (streak).")
public interface CountDto {
    @Schema(description = "длина серии в днях")
    Integer getCon();
    @Schema(description = "первый день серии")
    LocalDate getMinDate();
    }
