package com.shangrila.aptmaintenance.service;

import com.shangrila.aptmaintenance.dto.ExpenseDTO;
import com.shangrila.aptmaintenance.entity.Expense;
import com.shangrila.aptmaintenance.entity.Quarter;
import com.shangrila.aptmaintenance.mapper.ExpenseMapper;
import com.shangrila.aptmaintenance.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository repo;
    private final ExpenseMapper mapper;

    public void addExpense(ExpenseDTO dto) {

        Expense e = new Expense();

        e.setDescription(dto.getDescription());
        e.setAmount(dto.getAmount());
        e.setDate(dto.getDate());

        // 🔥 AUTO SET
        e.setQuarter(getQuarter(dto.getDate()));

        e.setYear(dto.getDate().getYear());

        repo.save(e);
        //repo.save(mapper.toEntity(dto));
    }

    public List<Expense> getByQuarter(int year, Quarter q) {
        return repo.findByYearAndQuarter(year, q);
    }

    public void updateExpense(Long id, ExpenseDTO dto) {
        Expense e = repo.findById(id).orElseThrow();

        e.setDescription(dto.getDescription());
        e.setAmount(dto.getAmount());
        e.setQuarter(dto.getQuarter());
        e.setYear(dto.getYear());

        repo.save(e);
    }

    private Quarter getQuarter(LocalDate date) {
        int month = date.getMonthValue();

        if (month <= 3) return Quarter.Q1;
        else if (month <= 6) return Quarter.Q2;
        else if (month <= 9) return Quarter.Q3;
        else return Quarter.Q4;
    }

    public void deleteExpense(Long id) {
        repo.deleteById(id);
    }
}
