package com.shangrila.aptmaintenance.controller;

import com.shangrila.aptmaintenance.dto.ExpenseDTO;
import com.shangrila.aptmaintenance.entity.Expense;
import com.shangrila.aptmaintenance.entity.Quarter;
import com.shangrila.aptmaintenance.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService service;

    @PostMapping
    public void add(@RequestBody ExpenseDTO dto) {
        service.addExpense(dto);
    }

    @GetMapping
    public List<Expense> list(@RequestParam int year,
                              @RequestParam Quarter quarter) {
        return service.getByQuarter(year, quarter);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody ExpenseDTO dto) {
        service.updateExpense(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteExpense(id);
    }
}
