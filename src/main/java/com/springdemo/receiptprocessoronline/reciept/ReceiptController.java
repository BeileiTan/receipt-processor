package com.springdemo.receiptprocessoronline.reciept;

import com.springdemo.receiptprocessoronline.system.IdData;
import com.springdemo.receiptprocessoronline.system.PointsData;
import com.springdemo.receiptprocessoronline.system.Result;
import com.springdemo.receiptprocessoronline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Receipt Processor System!";
    }

    @GetMapping("/receipts/{id}/points")
    public Result findReceiptById(@PathVariable String id){
        Receipt receipt = this.receiptService.findById(id);
        int points = receiptService.calculatePoints(receipt);
        PointsData pointsData = new PointsData(points);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", pointsData);
    }

    @PostMapping("/receipts/process")
    public Result addReceipt(@Valid @RequestBody Receipt receipt){
        Receipt savedReceipt = this.receiptService.save(receipt);
        IdData idData = new IdData(savedReceipt.getId());
        return new Result(true, StatusCode.SUCCESS, "Add Success", idData);
    }

}

