package com.program.model;

import java.time.LocalTime;

public class EachRecord {


    private String name;

    public LocalTime getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(LocalTime recordTime) {
        this.recordTime = recordTime;
    }

    private LocalTime recordTime;
    private String action;
    private boolean valid;


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


    @Override
    public String toString() {
        return "EachRecord{" +
                ", name='" + name + '\'' +
                ", recordTime=" + recordTime +
                ", action='" + action + '\'' +
                ", valid=" + valid +
                '}';
    }

    public void setAction(String action) {
        this.action = action;
    }

    public EachRecord() {}


}
