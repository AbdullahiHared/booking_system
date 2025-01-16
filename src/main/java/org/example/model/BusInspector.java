package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class BusInspector extends ProfitManager{
    private int inspectorId;
    private final String name;

    // Constructor
    public BusInspector(int inspectorId, String name) {
        this.inspectorId = inspectorId;
        this.name = name;
    }

    // Getters and Setters
    public int getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(int inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getName() {
        return name;
    }

    public void getCurrentProfit() {
        super.getTotalProfit();
    }
}
