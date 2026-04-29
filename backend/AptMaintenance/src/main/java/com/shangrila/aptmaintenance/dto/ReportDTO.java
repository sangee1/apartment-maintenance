package com.shangrila.aptmaintenance.dto;

import com.shangrila.aptmaintenance.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private double totalExpenses;
    private double totalPaid;
    private double pending;
    private List<Payment> payments;
}
