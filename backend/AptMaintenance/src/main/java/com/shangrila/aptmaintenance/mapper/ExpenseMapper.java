package com.shangrila.aptmaintenance.mapper;

import com.shangrila.aptmaintenance.dto.ExpenseDTO;
import com.shangrila.aptmaintenance.entity.Expense;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ExpenseMapper {
    public Expense toEntity(ExpenseDTO dto) {
        Expense e = new Expense();
        e.setDescription(dto.getDescription());
        e.setAmount(dto.getAmount());
        e.setQuarter(dto.getQuarter());
        e.setYear(dto.getYear());
        e.setDate(LocalDate.now());
        return e;
    }
}
