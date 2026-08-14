package org.parabiriktirme.savingsapp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="amount", nullable = false)
    private BigDecimal amount;

    @Column(name="date", nullable = false)
    private LocalDate date;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="is_recurring")
    private boolean isRecurring;


}
