package org.example.model;
import java.util.ArrayList;
import java.util.List;

public class ProfitManager {

    // variables
    private double totalProfit;
    private final double adultTicketPrice = 299;
    private final double childTicketPrice = 149.50;
    private List<String> profitLogs;

    // constructor
    public ProfitManager() {
        this.totalProfit = 0.0;
        this.profitLogs = new ArrayList<>();
    }

}
