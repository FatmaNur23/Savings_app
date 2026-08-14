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

        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;

        BigDecimal netSavings = totalIncome.subtract(totalExpense);


        BigDecimal goalMonthlySavings = goal.getMonthlySavings() != null ? goal.getMonthlySavings() : BigDecimal.ZERO;
        BigDecimal targetAvailableAmount = netSavings.subtract(goalMonthlySavings);

        BigDecimal targetAmount = goal.getTargetAmount() != null ? goal.getTargetAmount() : BigDecimal.ZERO;
        BigDecimal currentAmount = goal.getCurrentAmount() != null ? goal.getCurrentAmount() : BigDecimal.ZERO;
        BigDecimal remainingAmount = targetAmount.subtract(currentAmount);

        BigDecimal dailyAvailableAmount = targetAvailableAmount.divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP);

        int estimatedDaysLeft = 1;
        if (dailyAvailableAmount.compareTo(BigDecimal.ZERO) > 0 && remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
            estimatedDaysLeft = remainingAmount.divide(dailyAvailableAmount, RoundingMode.CEILING).intValue();
        }

        int estimatedMonthsLeft = (int) Math.ceil((double) estimatedDaysLeft / 30);

        GoalCalculationResponse response = new GoalCalculationResponse();
        response.setGoalName(goal.getName());
        response.setTargetAmount(targetAmount);
        response.setCurrentAmount(currentAmount);
        response.setRemainingAmount(remainingAmount);
        response.setNetSavings(netSavings);
        response.setEstimatedMonthsLeft((double) estimatedMonthsLeft);
        response.setEstimatedDaysLeft((double) estimatedDaysLeft);
        response.setStatusMessage("Harika gidiyorsun! Bu hedefe yaklaşık " + estimatedDaysLeft + " günde ulaşabilirsin.");

        return response;
    }

}
