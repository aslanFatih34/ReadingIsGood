package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.StatisticDto;
import com.getir.readingisgood.service.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(
        title = "Getir API",
        version = "v1"
))
public class StatisticsController {

    private final OrderService orderService;

    @GetMapping(path = "/{customerId}")
    @Operation(summary = "Get statistics list by customer uuid")
    public ResponseEntity<List<StatisticDto>> getCustomers(@PathVariable String customerId) {
        List<StatisticDto> statisticDtos = orderService.getStatistics(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(statisticDtos);
    }
}
