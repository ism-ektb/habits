package ru.ism.testBank.domain.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Count {
    private Long rn;
    private LocalDate grp;
    private LocalDate d;
}
