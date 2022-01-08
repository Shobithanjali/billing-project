package com.program.model;

import java.time.LocalTime;

public class UserSession {

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;


    public UserSession(String name) {
        this.name = name;
    }


    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }


}
