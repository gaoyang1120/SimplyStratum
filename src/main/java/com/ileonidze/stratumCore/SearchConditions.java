package com.ileonidze.stratumCore;

public class SearchConditions {
    private Integer index = null;
    private Integer timeFrame = 1;

    public Integer getIndex() {
        return index;
    }

    public Integer getTimeFrame() {
        return timeFrame;
    }
    public SearchConditions setTimeFrame(Integer timeFrame) {
        this.timeFrame = timeFrame;
        return this;
    }
    public SearchConditions setIndex(Integer index) {
        this.index = index;
        return this;
    }
}