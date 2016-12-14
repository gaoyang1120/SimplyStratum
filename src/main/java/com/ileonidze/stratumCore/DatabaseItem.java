package com.ileonidze.stratumCore;

import java.util.Date;

public class DatabaseItem implements Comparable {
    private Float price;
    private Date date;

    public DatabaseItem(CSVItem item, int index){
        this.date = item.getDate();
        this.price = item.getClose();
    }
    public int compareTo(Object anotherItem){
        DatabaseItem item = (DatabaseItem) anotherItem;
        return this.date.compareTo(item.getDate());
    }

    public int getTimestamp(){
        return (int) Math.floor(date.getTime()/1000);
    }

    public Float getPrice() {
        return price;
    }
    public Date getDate() {
        return date;
    }
    @Override
    public String toString() {
        return "["+date+" ("+date.getTime()/1000+"):"+ price +"]";
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