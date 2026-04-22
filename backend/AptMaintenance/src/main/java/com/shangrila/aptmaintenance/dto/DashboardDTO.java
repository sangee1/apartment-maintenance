package com.shangrila.aptmaintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    private BigDecimal totalExpenses;
    private BigDecimal totalCollected;
    private BigDecimal balance;
    private BigDecimal pending;
}
