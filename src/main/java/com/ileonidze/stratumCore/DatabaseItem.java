package com.ileonidze.stratumCore;

import com.ileonidze.stratumIndicators.Indicator;
import com.ileonidze.stratumIndicators.IndicatorsCollection;

import java.util.Date;
import java.util.HashMap;

public class DatabaseItem implements Comparable {
    private double value;
    private Date date;
    private HashMap<String,Double> indicators = new HashMap<>();

    public DatabaseItem(CSVItem item, int index){
        this.date = item.getDate();
        this.value = item.getClose();
        for(int i =0;i<IndicatorsCollection.getCollection().size();i++){
            indicators.put(IndicatorsCollection.getCollection().get(i).name,IndicatorsCollection.getCollection().get(i).proceed(index,this));
        }
    }
    public int compareTo(Object anotherItem){
        DatabaseItem item = (DatabaseItem) anotherItem;
        return this.date.compareTo(item.getDate());
    }
    public double getValue() {
        return value;
    }
    public Date getDate() {
        return date;
    }
    @Override
    public String toString() {
        return "["+date+" ("+date.getTime()/1000+"):"+value+"]";
    }
    public HashMap<String, Double> getIndicators() {
        return indicators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatabaseItem that = (DatabaseItem) o;

        return date.equals(that.date);

    }
    @Override
    public int hashCode() {
        return (int) date.getTime();
    }
}