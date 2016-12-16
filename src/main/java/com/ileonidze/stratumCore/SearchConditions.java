package com.ileonidze.stratumCore;

public class SearchConditions {
    private Integer index = null;
    private int timeFrame = 1;
    private String[] indicators = null;
    private String indicator = null;
    private float[] inputData = null;
    private Integer deathPointer = null;
    private int period = 21;
    private float deviation = 0f;
    private int futureDistance = 1;
    private boolean strictMovements = false;

    public Integer getIndex() {
        return index;
    }

    public int getTimeFrame() {
        return timeFrame;
    }
    public SearchConditions setTimeFrame(int timeFrame) {
        this.timeFrame = timeFrame;
        return this;
    }
    public SearchConditions setIndex(int index) {
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

    public float[] getInputData() {
        return inputData;
    }

    public SearchConditions setInputData(float[] inputData) {
        this.inputData = inputData;
        return this;
    }

    public Integer getDeathPointer() {
        return deathPointer;
    }

    public SearchConditions setDeathPointer(int deathPointer) {
        this.deathPointer = deathPointer;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public SearchConditions setPeriod(int period) {
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

    public float getDeviation() {
        return deviation;
    }

    public SearchConditions setDeviation(float deviation) {
        this.deviation = deviation;
        return this;
    }

    public int getFutureDistance() {
        return futureDistance;
    }

    public SearchConditions setFutureDistance(int futureDistance) {
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