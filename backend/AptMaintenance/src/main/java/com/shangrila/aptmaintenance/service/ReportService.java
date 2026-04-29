package com.shangrila.aptmaintenance.service;

import com.shangrila.aptmaintenance.dto.ReportDTO;
import com.shangrila.aptmaintenance.entity.Expense;
import com.shangrila.aptmaintenance.entity.Payment;
import com.shangrila.aptmaintenance.entity.PaymentStatus;
import com.shangrila.aptmaintenance.entity.Quarter;
import com.shangrila.aptmaintenance.repository.ExpenseRepository;
import com.shangrila.aptmaintenance.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ExpenseRepository expenseRepo;
    private final PaymentRepository paymentRepo;

    public ReportDTO generateQuarterlyReport(int year, int quarter) {
        Quarter q = Quarter.values()[quarter - 1];
        List<Expense> expenses =
                expenseRepo.findByYearAndQuarter(year, q);

        List<Payment> payments =
                paymentRepo.findByYearAndQuarter(year, q);

        double totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();

        double totalPaid = payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID)
                .map(Payment::getAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();

        double pending = totalExpenses - totalPaid;

        return new ReportDTO(totalExpenses, totalPaid, pending, payments);
    }
}
