package com.errandGo.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to Task
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_method", length = 64)
    private String paymentMethod;

    @Column(name = "transaction_id", length = 128)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentStatus status = PaymentStatus.PENDING;

    private Boolean refunded = false;

    @Column(name = "commission_pct")
    private Float commissionPct = 0f;

    @Column(name = "admin_share", precision = 10, scale = 2)
    private BigDecimal adminShare;

    @Column(name = "errand_boy_share", precision = 10, scale = 2)
    private BigDecimal errandBoyShare;


    public enum PaymentStatus {
        SUCCESS,
        PENDING,
        FAILED
    }
}