package com.shangrila.aptmaintenance.controller;

import com.shangrila.aptmaintenance.dto.DashboardDTO;
import com.shangrila.aptmaintenance.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService service;

    @GetMapping("/summary")
    public DashboardDTO summary(@RequestParam int year) {
        return service.getSummary(year);
    }
}
