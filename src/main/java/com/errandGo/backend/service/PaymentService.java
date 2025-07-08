package com.errandGo.backend.service;

import com.errandGo.backend.entities.Payment;
import com.errandGo.backend.entities.Task;
import com.errandGo.backend.repositories.PaymentRepository;
import com.errandGo.backend.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TaskRepository taskRepository;

    private static final float DEFAULT_COMMISSION_PERCENT = 15.0f;


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with id: " + id));
    }

    @Transactional
    public Payment createPayment(Long taskId, BigDecimal amount, String method, String transactionId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

        float commission = DEFAULT_COMMISSION_PERCENT;
        BigDecimal adminShare = amount.multiply(BigDecimal.valueOf(commission / 100));
        BigDecimal errandBoyShare = amount.subtract(adminShare);

        Payment payment = Payment.builder()
                .task(task)
                .amount(amount)
                .paymentMethod(method)
                .transactionId(transactionId)
                .status(Payment.PaymentStatus.SUCCESS)
                .refunded(false)
                .commissionPct(commission)
                .adminShare(adminShare)
                .errandBoyShare(errandBoyShare)
                .build();

        return paymentRepository.save(payment);
    }


    @Transactional
    public Payment updatePayment(Long id, Payment updatedPayment) {
        Payment existing = getPaymentById(id);
        existing.setAmount(updatedPayment.getAmount());
        existing.setPaymentMethod(updatedPayment.getPaymentMethod());
        existing.setTransactionId(updatedPayment.getTransactionId());
        existing.setStatus(updatedPayment.getStatus());
        existing.setRefunded(updatedPayment.getRefunded());
        existing.setCommissionPct(updatedPayment.getCommissionPct());
        existing.setAdminShare(updatedPayment.getAdminShare());
        existing.setErrandBoyShare(updatedPayment.getErrandBoyShare());

        return paymentRepository.save(existing);
    }


    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Payment not found");
        }
        paymentRepository.deleteById(id);
    }


    @Transactional
    public void refundPayment(Long id) {
        Payment payment = getPaymentById(id);
        payment.setRefunded(true);
        payment.setStatus(Payment.PaymentStatus.FAILED);
        paymentRepository.save(payment);
    }
}
