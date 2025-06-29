package com.errandGo.backend.controller;

import com.errandGo.backend.entities.Payment;
import com.errandGo.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // ✅ Get all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // ✅ Get a specific payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    // ✅ Create a new payment
    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestParam Long taskId,
            @RequestParam BigDecimal amount,
            @RequestParam String method,
            @RequestParam String transactionId
    ) {
        return ResponseEntity.ok(paymentService.createPayment(taskId, amount, method, transactionId));
    }

    // ✅ Update a payment
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @RequestBody Payment updatedPayment
    ) {
        return ResponseEntity.ok(paymentService.updatePayment(id, updatedPayment));
    }

    // ✅ Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Refund a payment
    @PostMapping("/{id}/refund")
    public ResponseEntity<Void> refundPayment(@PathVariable Long id) {
        paymentService.refundPayment(id);
        return ResponseEntity.ok().build();
    }
}
