package com.shangrila.aptmaintenance.repository;

import com.shangrila.aptmaintenance.entity.Expense;
import com.shangrila.aptmaintenance.entity.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    List<Expense> findByYearAndQuarter(int year, Quarter quarter);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.year=:year")
    BigDecimal totalExpenses(int year);
}
