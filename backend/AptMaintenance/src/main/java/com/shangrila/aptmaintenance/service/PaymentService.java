package com.shangrila.aptmaintenance.service;

import com.shangrila.aptmaintenance.dto.PaymentCellDTO;
import com.shangrila.aptmaintenance.dto.PaymentGridDTO;
import com.shangrila.aptmaintenance.entity.Payment;
import com.shangrila.aptmaintenance.entity.PaymentStatus;
import com.shangrila.aptmaintenance.entity.Quarter;
import com.shangrila.aptmaintenance.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepo;

    public List<PaymentGridDTO> getGrid(int year) {

        List<Payment> payments = paymentRepo.findByYear(year);

        Map<String, List<PaymentCellDTO>> map = new HashMap<>();

        for (Payment p : payments) {

            map.putIfAbsent(p.getFlat().getName(), new ArrayList<>());

            PaymentCellDTO cell = new PaymentCellDTO();
            cell.setId(p.getId());
            cell.setQuarter(p.getQuarter());
            cell.setStatus(p.getStatus());

            map.get(p.getFlat().getName()).add(cell);
        }

        return map.entrySet().stream().map(e -> {
            PaymentGridDTO dto = new PaymentGridDTO();
            dto.setFlatName(e.getKey());
            dto.setPayments(e.getValue());
            return dto;
        }).toList();
    }

    public void toggle(Long paymentId) {
        Payment p = paymentRepo.findById(paymentId).orElseThrow();

        if (p.getStatus() == PaymentStatus.PAID) {
            p.setStatus(PaymentStatus.UNPAID);
            p.setPaymentDate(null);
        } else {
            p.setStatus(PaymentStatus.PAID);
            p.setPaymentDate(LocalDate.now());
        }

        paymentRepo.save(p);
    }
}
