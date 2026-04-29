package com.shangrila.aptmaintenance.dto;

import com.shangrila.aptmaintenance.entity.Payment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportDTO {
    private double totalExpenses;
    private double totalPaid;
    private double pending;
    private List<Payment> payments;
}
