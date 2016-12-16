package com.ileonidze.stratumPredictions;

import com.ileonidze.stratumCore.SearchConditions;
import com.ileonidze.stratumIndicators.Indicator;

import java.util.List;

public class Prediction {
    private Integer direction; //0;
    private Integer futureDistance; //0;
    private List<float[]> similarities;
    private float probability; //50f;
    private Indicator indicator;
    private float deviation; //0f;
    private Integer timeFrame; //1;
    private Integer period; //21;

    public Prediction predict(SearchConditions conditions){
        return null;
    }

    public Integer getDirection() {
        return direction;
    }

    public Prediction setDirection(Integer direction) {
        this.direction = direction;
        return this;
    }

    public Integer getFutureDistance() {
        return futureDistance;
    }

    public Prediction setFutureDistance(Integer futureDistance) {
        this.futureDistance = futureDistance;
        return this;
    }

    public List<float[]> getSimilarities() {
        return similarities;
    }

    public Prediction setSimilarities(List<float[]> similarities) {
        this.similarities = similarities;
        return this;
    }

    public float getProbability() {
        return probability;
    }

    public Prediction setProbability(float probability) {
        this.probability = probability;
        return this;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public Prediction setIndicator(Indicator indicator) {
        this.indicator = indicator;
        return this;
    }

    public float getDeviation() {
        return deviation;
    }

    public Prediction setDeviation(float deviation) {
        this.deviation = deviation;
        return this;
    }

    public Integer getTimeFrame() {
        return timeFrame;
    }

    public Prediction setTimeFrame(Integer timeFrame) {
        this.timeFrame = timeFrame;
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public Prediction setPeriod(Integer period) {
        this.period = period;
        return this;
    }
}