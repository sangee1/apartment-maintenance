package com.shangrila.aptmaintenance.controller;

import com.shangrila.aptmaintenance.dto.PaymentGridDTO;
import com.shangrila.aptmaintenance.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping("/grid")
    public List<PaymentGridDTO> grid(@RequestParam int year) {
        return service.getGrid(year);
    }

    @PutMapping("/{id}/toggle")
    public void toggle(@PathVariable Long id) {
        service.toggle(id);
    }
}
