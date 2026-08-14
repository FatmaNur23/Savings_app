package org.parabiriktirme.savingsapp.Service;

import lombok.RequiredArgsConstructor;
import org.parabiriktirme.savingsapp.Entity.Expense;
import org.parabiriktirme.savingsapp.Entity.Income;
import org.parabiriktirme.savingsapp.Repository.ExpenseRepository;
import org.parabiriktirme.savingsapp.Repository.IncomeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringTransactionService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void processRecurringTransactions() {
        LocalDate today = LocalDate.now();
        int currentDay = today.getDayOfMonth(); // Bugün ayın kaçı? (Örn: 15)


        List<Income> recurringIncomes = incomeRepository.findByIsRecurringTrue();
        for (Income income : recurringIncomes) {
            if (income.getDate().getDayOfMonth() == currentDay) {
                Income newIncome = Income.builder()
                        .title(income.getTitle() + " (Otomatik)")
                        .amount(income.getAmount())
                        .date(today)
                        .isRecurring(false)
                        .build();
                incomeRepository.save(newIncome);
            }
        }


        List<Expense> recurringExpenses = expenseRepository.findByIsRecurringTrue();
        for (Expense expense : recurringExpenses) {
            if (expense.getDate().getDayOfMonth() == currentDay) {
                Expense newExpense = Expense.builder()
                        .title(expense.getTitle() + " (Otomatik)")
                        .amount(expense.getAmount())
                        .date(today)
                        .category(expense.getCategory())
                        .isRecurring(false)
                        .build();
                expenseRepository.save(newExpense);
            }
        }

        System.out.println("Zamanlanmış Görev Çalıştı: " + today + " tarihli otomatik işlemler eklendi.");
    }
}
