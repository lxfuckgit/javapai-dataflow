package com.javapai.dataflow.collector.utils;

import java.util.Date;

public class KeyValueForDate {

    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "KeyValueForDate{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
