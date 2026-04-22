package com.shangrila.aptmaintenance.dto;

import com.shangrila.aptmaintenance.entity.Quarter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private String description;
    private BigDecimal amount;
    private Quarter quarter;
    private LocalDate date;
    private int year;
}
