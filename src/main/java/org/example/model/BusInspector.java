package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class BusInspector {
    private String inspectorId;
    private String busNumber;
    private String busDate;
    private Double currentProfit;
    private List <String> inspectorLogs;

    // Constructor
    public BusInspector(String inspectorId, String busNumber, String busDate, Double currentProfit) {
        this.inspectorId = inspectorId;
        this.busNumber = busNumber;
        this.busDate = busDate;
        this.currentProfit = currentProfit;
        this.inspectorLogs = new ArrayList<String>();
    }




}
