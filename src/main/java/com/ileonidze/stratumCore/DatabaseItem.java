package com.ileonidze.stratumCore;

import java.util.Date;

public class DatabaseItem implements Comparable {
    private float value;
    private Date date;

    public DatabaseItem(CSVItem item, int index){
        this.date = item.getDate();
        this.value = item.getClose();
    }

    public DatabaseItem(float price, Date date) {
        this.value = price;
        this.date = date;
    }

    public int compareTo(Object anotherItem){
        DatabaseItem item = (DatabaseItem) anotherItem;
        return this.date.compareTo(item.getDate());
    }

    public int getTimestamp(){
        return (int) Math.floor(date.getTime()/1000);
    }

    public float getValue() {
        return value;
    }
    public Date getDate() {
        return date;
    }
    @Override
    public String toString() {
        return "["+date+" ("+date.getTime()/1000+"):"+ value +"]";
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