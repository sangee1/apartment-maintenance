package com.shangrila.aptmaintenance.service;

import com.shangrila.aptmaintenance.dto.ReportDTO;
import com.shangrila.aptmaintenance.entity.Expense;
import com.shangrila.aptmaintenance.entity.Payment;
import com.shangrila.aptmaintenance.repository.ExpenseRepository;
import com.shangrila.aptmaintenance.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ExpenseRepository expenseRepo;
    private final PaymentRepository paymentRepo;

    public ReportDTO generateQuarterlyReport(int year, int quarter) {
        List<Expense> expenses =
                expenseRepo.findByYearAndQuarter(year, quarter);

        List<Payment> payments =
                paymentRepo.findByYearAndQuarter(year, quarter);

        double totalExpenses = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double totalPaid = payments.stream()
                .filter(p -> p.getStatus().equals("PAID"))
                .mapToDouble(Payment::getAmount)
                .sum();

        double pending = totalExpenses - totalPaid;

        return new ReportDTO(totalExpenses, totalPaid, pending, payments);
    }
}
