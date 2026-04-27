package com.shangrila.aptmaintenance.config;

import com.shangrila.aptmaintenance.entity.Flat;
import com.shangrila.aptmaintenance.entity.Payment;
import com.shangrila.aptmaintenance.entity.PaymentStatus;
import com.shangrila.aptmaintenance.entity.Quarter;
import com.shangrila.aptmaintenance.repository.FlatRepository;
import com.shangrila.aptmaintenance.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final FlatRepository flatRepo;
    private final PaymentRepository paymentRepo;

    @Override
    public void run(String... args) {

        List<String> flats = List.of(
                "A","B","C",
                "A-1","A-2","A-3",
                "B-1","B-2","B-3",
                "C-1","C-2","C-3"
        );

        // Insert flats only if missing
        flats.forEach(name -> {
            if (!flatRepo.existsByName(name)) {
                Flat f = new Flat();
                f.setName(name);
                flatRepo.save(f);
            }
        });

        // Create payments for current year if not exist
        int year = LocalDate.now().getYear();
        List<Flat> allFlats = flatRepo.findAll();

        for (Flat flat : allFlats) {
            for (Quarter q : Quarter.values()) {

                boolean exists = paymentRepo
                        .findByFlatIdAndYearAndQuarter(flat.getId(), year, q)
                        .isPresent();

                if (!exists) {
                    Payment p = new Payment();
                    p.setFlat(flat);
                    p.setYear(year);
                    p.setQuarter(q);
                    p.setAmount(BigDecimal.valueOf(3000)); // default maintenance
                    p.setStatus(PaymentStatus.UNPAID);

                    paymentRepo.save(p);
                }
            }
        }

    }
}
