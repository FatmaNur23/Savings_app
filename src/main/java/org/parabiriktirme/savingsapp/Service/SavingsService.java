package org.parabiriktirme.savingsapp.Service;

import org.parabiriktirme.savingsapp.DTO.GoalCalculationResponse;
import org.parabiriktirme.savingsapp.Entity.Expense;
import org.parabiriktirme.savingsapp.Entity.Goal;
import org.parabiriktirme.savingsapp.Entity.Income;
import org.parabiriktirme.savingsapp.Repository.ExpenseRepository;
import org.parabiriktirme.savingsapp.Repository.GoalRepository;
import org.parabiriktirme.savingsapp.Repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingsService {

    private final GoalRepository goalRepository;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;


    public Goal saveGoal(Goal goal) { return goalRepository.save(goal); }
    public Income saveIncome(Income income) { return incomeRepository.save(income); }
    public Expense saveExpense(Expense expense) { return expenseRepository.save(expense); }


    public List<Goal> getAllGoals() { return goalRepository.findAll(); }
    public List<Income> getAllIncomes() { return incomeRepository.findAll(); }
    public List<Expense> getAllExpenses() { return expenseRepository.findAll(); }


    public GoalCalculationResponse calculateGoalStatus(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Hedef bulunamadı: " + goalId));

        BigDecimal totalIncome = incomeRepository.getTotalIncome();
        BigDecimal totalExpense = expenseRepository.getTotalExpense();


        BigDecimal netSavings = totalIncome.subtract(totalExpense);


        BigDecimal remainingAmount = goal.getTargetAmount().subtract(goal.getCurrentAmount());


        if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return GoalCalculationResponse.builder()
                    .goalId(goal.getId())
                    .goalName(goal.getName())
                    .targetAmount(goal.getTargetAmount())
                    .currentAmount(goal.getCurrentAmount())
                    .remainingAmount(BigDecimal.ZERO)
                    .netSavings(netSavings)
                    .estimatedMonthsLeft(0.0)
                    .estimatedDaysLeft(0.0)
                    .statusMessage("Tebrikler! Hedefinize ulaştınız.")
                    .build();
        }


        if (netSavings.compareTo(BigDecimal.ZERO) <= 0) {
            return GoalCalculationResponse.builder()
                    .goalId(goal.getId())
                    .goalName(goal.getName())
                    .targetAmount(goal.getTargetAmount())
                    .currentAmount(goal.getCurrentAmount())
                    .remainingAmount(remainingAmount)
                    .netSavings(netSavings)
                    .estimatedMonthsLeft(-1.0)
                    .estimatedDaysLeft(-1.0)
                    .statusMessage("Giderleriniz gelirinizden fazla veya eşit. Hedefe ulaşmak için harcamalarınızı kısmanız gerekiyor.")
                    .build();
        }


        BigDecimal monthsLeft = remainingAmount.divide(netSavings, 2, RoundingMode.HALF_UP);
        double daysLeft = monthsLeft.doubleValue() * 30.0;

        return GoalCalculationResponse.builder()
                .goalId(goal.getId())
                .goalName(goal.getName())
                .targetAmount(goal.getTargetAmount())
                .currentAmount(goal.getCurrentAmount())
                .remainingAmount(remainingAmount)
                .netSavings(netSavings)
                .estimatedMonthsLeft(monthsLeft.doubleValue())
                .estimatedDaysLeft(daysLeft)
                .statusMessage(String.format("Mevcut harcama alışkanlıklarınızla hedefinize yaklaşık %.1f ay (%.0f gün) sonra ulaşabilirsiniz.", monthsLeft.doubleValue(), daysLeft))
                .build();
    }



}
