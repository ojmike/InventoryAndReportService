package com.reportmanager.controller;

import com.reportmanager.payload.request.OrderFilter;
import com.reportmanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ReportController {

    private final OrderService orderService;


    @GetMapping("/reports")
    public ResponseEntity getReports(@RequestBody OrderFilter orderFilter){
        return new ResponseEntity(orderService.fetchReport(orderFilter), HttpStatus.OK);
    }
}
