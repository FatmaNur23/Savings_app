package org.parabiriktirme.savingsapp.Entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name="created_at",insertable = false, updatable = false)
    private LocalDateTime createdAt;


}
