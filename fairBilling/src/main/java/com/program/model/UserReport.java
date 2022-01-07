package com.program.model;

public class UserReport {

    private String name;
    private int numberOfSessions;
    private int billableTimeInSeconds;

    public UserReport(String name, int numberOfSessions, int billableTimeInSeconds) {
        this.name = name;
        this.numberOfSessions = numberOfSessions;
        this.billableTimeInSeconds = billableTimeInSeconds;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "name='" + name + '\'' +
                ", numberOfSessions=" + numberOfSessions +
                ", billableTimeInSeconds=" + billableTimeInSeconds +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(int numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    public int getBillableTimeInSeconds() {
        return billableTimeInSeconds;
    }

    public void setBillableTimeInSeconds(int billableTimeInSeconds) {
        this.billableTimeInSeconds = billableTimeInSeconds;
    }
}
