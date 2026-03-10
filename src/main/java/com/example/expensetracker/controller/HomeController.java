package com.example.expensetracker.controller;

import com.example.expensetracker.entity.Transaction;
import com.example.expensetracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    private final TransactionService transactionService;

    @Autowired
    public HomeController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String index(Model model,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        LocalDate now = LocalDate.now();
        int selectedMonth = (month != null) ? month : now.getMonthValue();
        int selectedYear = (year != null) ? year : now.getYear();

        List<Transaction> transactions = transactionService.getTransactionsByMonthAndYear(selectedMonth, selectedYear);
        BigDecimal totalIncome = transactionService.calculateTotalIncome(transactions);
        BigDecimal totalExpense = transactionService.calculateTotalExpense(transactions);
        BigDecimal balance = transactionService.calculateBalance(totalIncome, totalExpense);

        model.addAttribute("title", "Quản lý Chi tiêu");
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("balance", balance);
        model.addAttribute("selectedMonth", selectedMonth);
        model.addAttribute("selectedYear", selectedYear);

        // Thêm Object rỗng để map vào form Modal thêm giao dịch
        model.addAttribute("newTransaction", new Transaction());

        return "index";
    }

    @PostMapping("/transaction/add")
    public String addTransaction(@ModelAttribute Transaction transaction) {
        if (transaction.getDate() == null) {
            transaction.setDate(LocalDate.now()); // Fallback nếu user không điền date thì lấy hôm nay
        }
        transactionService.saveTransaction(transaction);
        return "redirect:/";
    }

    @PostMapping("/transaction/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return "redirect:/";
    }

    @GetMapping("/transaction/edit/{id}")
    public String editTransactionForm(@PathVariable Long id, Model model) {
        Transaction transaction = transactionService.getTransactionById(id);
        if (transaction == null) {
            return "redirect:/"; // Hoặc chuyển sang trang báo lỗi
        }
        model.addAttribute("title", "Sửa Giao dịch");
        model.addAttribute("transaction", transaction);
        return "edit";
    }

    @PostMapping("/transaction/edit/{id}")
    public String editTransactionSubmit(@PathVariable Long id, @ModelAttribute Transaction transaction) {
        if (transaction.getDate() == null) {
            transaction.setDate(LocalDate.now());
        }
        // Đảm bảo ID không thay đổi
        transaction.setId(id);
        transactionService.saveTransaction(transaction);
        return "redirect:/";
    }
}
