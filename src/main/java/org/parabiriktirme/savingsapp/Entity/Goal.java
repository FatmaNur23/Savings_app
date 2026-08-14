package org.parabiriktirme.savingsapp.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="target_amount",nullable = false)
    private BigDecimal targetAmount;

    @Column(name="current_amount", nullable = false)
    private BigDecimal currentAmount;

    @Column(name="target_date")
    private LocalDate targetDate;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    // Hedef dışı, kullanıcının aylık düzenli kenara ayırdığı birikim tutarı
    @Column(name="monthly_savings", nullable = false)
    private BigDecimal monthlySavings = BigDecimal.ZERO;



}
