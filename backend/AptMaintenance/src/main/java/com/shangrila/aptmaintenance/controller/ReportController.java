package com.shangrila.aptmaintenance.controller;

import com.shangrila.aptmaintenance.dto.ReportDTO;
import com.shangrila.aptmaintenance.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/quarterly")
    public ReportDTO getQuarterlyReport(
            @RequestParam int year,
            @RequestParam int quarter) {

        return reportService.generateQuarterlyReport(year, quarter);
    }
}
