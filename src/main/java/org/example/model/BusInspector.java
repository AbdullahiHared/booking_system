package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class BusInspector {
    private int inspectorId;
    private int busNumber;
    private String busDate;
    private Double currentProfit;
    private List <String> inspectorLogs;

    // Constructor
    public BusInspector(int inspectorId, int busNumber, String busDate, Double currentProfit) {
        this.inspectorId = inspectorId;
        this.busNumber = busNumber;
        this.busDate = busDate;
        this.currentProfit = currentProfit;
        this.inspectorLogs = new ArrayList<String>();
    }
}
