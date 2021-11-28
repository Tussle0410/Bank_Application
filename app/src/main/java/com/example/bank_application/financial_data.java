package com.example.bank_application;

import java.io.Serializable;

public class financial_data implements Serializable {
    String kinds,description,name;
    double interestRate;
    int interestCycle;

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getInterestCycle() {
        return interestCycle;
    }

    public void setInterestCycle(int interestCycle) {
        this.interestCycle = interestCycle;
    }

}
