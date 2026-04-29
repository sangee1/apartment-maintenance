package com.shangrila.aptmaintenance.repository;

import com.shangrila.aptmaintenance.entity.Payment;
import com.shangrila.aptmaintenance.entity.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByYear(int year);

    Optional<Payment> findByFlatIdAndYearAndQuarter(Long flatId, int year, Quarter quarter);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.year=:year AND p.status='PAID'")
    BigDecimal totalCollected(int year);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.year=:year AND p.status='UNPAID'")
    BigDecimal totalPending(int year);

    List<Payment> findByYearAndQuarter(int year, Quarter quarter);
}
