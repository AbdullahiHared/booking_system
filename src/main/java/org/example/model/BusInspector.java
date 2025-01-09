package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class BusInspector {
    private int inspectorId;
    private String busNumber;
    private String busDate;
    private Double currentProfit;
    private List<String> inspectorLogs = new ArrayList<>();

    // Constructor
    public BusInspector(int inspectorId, String busNumber, String busDate, Double currentProfit) {
        this.inspectorId = inspectorId;
        this.busNumber = busNumber;
        this.busDate = busDate;
        this.currentProfit = currentProfit;
    }

    // Getters and Setters
    public int getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(int inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusDate() {
        return busDate;
    }

    public void setBusDate(String busDate) {
        this.busDate = busDate;
    }

    public Double getCurrentProfit() {
        return currentProfit;
    }

    public void setCurrentProfit(Double currentProfit) {
        this.currentProfit = currentProfit;
    }

    public List<String> getInspectorLogs() {
        return inspectorLogs;
    }

    public void addInspectorLog(String log) {
        this.inspectorLogs.add(log);
    }
}
