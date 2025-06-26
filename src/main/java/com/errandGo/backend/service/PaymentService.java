package com.errandGo.backend.service;


import com.errandGo.backend.entities.Payment;
import com.errandGo.backend.entities.Task;
import com.errandGo.backend.repositories.PaymentRepository;
import com.errandGo.backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TaskRepository taskRepository;

    private final float DEFAULT_COMMISSION_PERCENT = 15.0f;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Transactional
    public Payment createPayment(Long taskId, BigDecimal amount, String method, String transactionId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) throw new IllegalArgumentException("Task not found");

        Task task = optionalTask.get();

        float commission = DEFAULT_COMMISSION_PERCENT;
        BigDecimal adminShare = amount.multiply(BigDecimal.valueOf(commission / 100));
        BigDecimal errandBoyShare = amount.subtract(adminShare);

        Payment payment = new Payment();
        payment.setTask(task);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        payment.setTransactionId(transactionId);
        payment.setStatus(Payment.PaymentStatus.valueOf("SUCCESS"));
        payment.setRefunded(false);
        payment.setCommissionPct(commission);
        payment.setAdminShare(adminShare);
        payment.setErrandBoyShare(errandBoyShare);

        return paymentRepository.save(payment);
    }

    public void refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        payment.setRefunded(true);
        payment.setStatus(Payment.PaymentStatus.valueOf("REFUNDED"));
        paymentRepository.save(payment);
    }
}
