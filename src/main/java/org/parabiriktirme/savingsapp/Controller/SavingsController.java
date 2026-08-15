package org.parabiriktirme.savingsapp.Controller;

import org.parabiriktirme.savingsapp.DTO.GoalCalculationResponse;
import org.parabiriktirme.savingsapp.Entity.Expense;
import org.parabiriktirme.savingsapp.Entity.Goal;
import org.parabiriktirme.savingsapp.Entity.Income;
import org.parabiriktirme.savingsapp.Repository.GoalRepository;
import org.parabiriktirme.savingsapp.Service.SavingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class SavingsController {

    @Autowired
    private SavingsService savingsService;

    @Autowired
    private GoalRepository goalRepository;


    @PostMapping("/goals")
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        return ResponseEntity.ok(savingsService.saveGoal(goal));
    }

    @GetMapping("/goals")
    public ResponseEntity<List<Goal>> getAllGoals() {
        return ResponseEntity.ok(savingsService.getAllGoals());
    }


    @PostMapping("/incomes")
    public ResponseEntity<Income> createIncome(@RequestBody Income income) {
        return ResponseEntity.ok(savingsService.saveIncome(income));
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<Income>> getAllIncomes() {
        return ResponseEntity.ok(savingsService.getAllIncomes());
    }


    @PostMapping("/expenses")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok(savingsService.saveExpense(expense));
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(savingsService.getAllExpenses());
    }


    @GetMapping("/goals/{goalId}/calculate")
    public ResponseEntity<GoalCalculationResponse> calculateGoalStatus(@PathVariable Long goalId) {
        return ResponseEntity.ok(savingsService.calculateGoalStatus(goalId));
    }

    @GetMapping("/completed")
    public List<Goal> getCompletedGoals() {
        return goalRepository.findByIsCompletedTrue();
    }


}