package com.program.model;

import java.time.LocalTime;

public class EachRecord {


    private String name;
    private LocalTime recordTime;
    private String action;
    private boolean valid;
    public EachRecord() {
    }

    public LocalTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalTime recordTime) {
        this.recordTime = recordTime;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


}
