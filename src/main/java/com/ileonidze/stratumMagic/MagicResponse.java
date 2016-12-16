package com.ileonidze.stratumMagic;

import com.ileonidze.stratumCore.DatabaseItem;

import java.util.List;

public class MagicResponse {
    //// TODO: 16.12.2016 make immutable
    private String directionString = "UNKNOWN";
    private int directionNumber = 0;
    private float probability = 50;
    private List<DatabaseItem[]> similarities = null;
    private boolean act = false;

    public String getDirectionString() {
        return directionString;
    }

    public int getDirectionNumber() {
        return directionNumber;
    }

    public float getProbability() {
        return probability;
    }

    public List<DatabaseItem[]> getSimilarities() {
        return similarities;
    }

    public MagicResponse setDirectionString(String directionString) {
        if(this.directionString.equals("UNKNOWN")) this.directionString = directionString;
        return this;
    }

    public MagicResponse setDirectionNumber(int directionNumber) {
        if(this.directionNumber!=0) this.directionNumber = directionNumber;
        return this;
    }

    public MagicResponse setProbability(float probability) {
        if(this.probability==50) this.probability = probability;
        return this;
    }

    public MagicResponse setSimilarities(List<DatabaseItem[]> similarities) {
        if(this.similarities==null) this.similarities = similarities;
        return this;
    }

    public boolean isAct() {
        return act;
    }

    public MagicResponse setAct(boolean act) {
        this.act = act;
        return this;
    }
}
