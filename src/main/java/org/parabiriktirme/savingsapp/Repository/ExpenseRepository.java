package org.parabiriktirme.savingsapp.Repository;

import org.parabiriktirme.savingsapp.Entity.Expense;
import org.parabiriktirme.savingsapp.Entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    BigDecimal getTotalExpense();

    List<Expense> findByIsRecurringTrue();

}
