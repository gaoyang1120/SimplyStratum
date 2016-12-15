package com.ileonidze.stratumCore;

public class SearchConditions {
    private Integer index = null;
    private Integer timeFrame = 1;
    private String[] indicators = null;
    private String indicator = null;
    private Float[] inputData = null;
    private Integer deathPointer = null;
    private Integer period = 21;
    private Float deviation = 0f;
    private Integer futureDistance = 1;
    private boolean strictMovements = false;

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
    public String[] getIndicators() {
        return indicators;
    }
    public SearchConditions setIndicators(String[] indicators) {
        this.indicators = indicators;
        return this;
    }

    public Float[] getInputData() {
        return inputData;
    }

    public SearchConditions setInputData(Float[] inputData) {
        this.inputData = inputData;
        return this;
    }

    public Integer getDeathPointer() {
        return deathPointer;
    }

    public SearchConditions setDeathPointer(Integer deathPointer) {
        this.deathPointer = deathPointer;
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public SearchConditions setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    public String getIndicator() {
        return indicator;
    }

    public SearchConditions setIndicator(String indicator) {
        this.indicator = indicator;
        return this;
    }

    public Float getDeviation() {
        return deviation;
    }

    public SearchConditions setDeviation(Float deviation) {
        this.deviation = deviation;
        return this;
    }

    public Integer getFutureDistance() {
        return futureDistance;
    }

    public SearchConditions setFutureDistance(Integer futureDistance) {
        this.futureDistance = futureDistance;
        return this;
    }

    public boolean isStrictMovements() {
        return strictMovements;
    }

    public SearchConditions setStrictMovements(boolean strictMovements) {
        this.strictMovements = strictMovements;
        return this;
    }
}