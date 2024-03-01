package com.project.carstore.order;

import java.util.List;

public class StockValidationResponse {
    private Order order;
    private List<String> stockIssues;

    public StockValidationResponse(Order order, List<String> stockIssues) {
        this.order = order;
        this.stockIssues = stockIssues;
    }

    // Getters and setters for order and stockIssues

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<String> getStockIssues() {
        return stockIssues;
    }

    public void setStockIssues(List<String> stockIssues) {
        this.stockIssues = stockIssues;
    }
}
