package com.example.expensebackend.dto.Response;

import java.math.BigDecimal;

public class BudgetResponse {
    private String userName;
    private String categoryName;
    private BigDecimal limitAmount;
    private Integer month;
    private Integer year;

    public BudgetResponse(String userName, String categoryName, BigDecimal limitAmount, Integer month, Integer year) {
        this.userName = userName;
        this.categoryName = categoryName;
        this.limitAmount = limitAmount;
        this.month = month;
        this.year = year;
    }

    public String getUserName() {
        return userName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public BigDecimal getLimitAmount() {
        return limitAmount;
    }
    public Integer getMonth() {
        return month;
    }
    public Integer getYear() {
        return year;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public BudgetResponse() {}
}
