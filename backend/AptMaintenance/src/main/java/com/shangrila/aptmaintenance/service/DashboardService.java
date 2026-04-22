package com.shangrila.aptmaintenance.service;

import com.shangrila.aptmaintenance.dto.DashboardDTO;
import com.shangrila.aptmaintenance.repository.ExpenseRepository;
import com.shangrila.aptmaintenance.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PaymentRepository paymentRepo;
    private final ExpenseRepository expenseRepo;

    public DashboardDTO getSummary(int year) {

        BigDecimal expenses = Optional.ofNullable(expenseRepo.totalExpenses(year))
                .orElse(BigDecimal.ZERO);

        BigDecimal collected = Optional.ofNullable(paymentRepo.totalCollected(year))
                .orElse(BigDecimal.ZERO);

        BigDecimal pending = Optional.ofNullable(paymentRepo.totalPending(year))
                .orElse(BigDecimal.ZERO);

        DashboardDTO dto = new DashboardDTO();
        dto.setTotalExpenses(expenses);
        dto.setTotalCollected(collected);
        dto.setPending(pending);
        dto.setBalance(collected.subtract(expenses));

        return dto;
    }
}
