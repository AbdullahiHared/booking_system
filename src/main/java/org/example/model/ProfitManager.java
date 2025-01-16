package org.example.model;
import org.example.database.SchemaInitializer;

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

    // getter for the total profit
    public double getTotalProfit() {
        return totalProfit;
    }

    // Method to add profit for a booking
    public void addProfit(boolean isAdult) {
        double ticketPrice = isAdult ? adultTicketPrice : childTicketPrice;
        totalProfit += ticketPrice;
        profitLogs.add("Added profit: " + ticketPrice + " | Total Profit: " + totalProfit);
    }

    // method to update totalProfit when refund is made
    public void deductProfit(boolean isAdult) {
        double ticketPrice = isAdult ? adultTicketPrice : childTicketPrice;
        totalProfit-= ticketPrice;
        profitLogs.add("Refunded amount: " + ticketPrice + ", Total profit: " + getTotalProfit());
    }

    public List<String> getProfitLogs () {
        return profitLogs;
    }

}
