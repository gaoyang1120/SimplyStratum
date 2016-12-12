import java.util.Date;

public class DatabaseItem implements Comparable {
    public DatabaseItem() {}
    public DatabaseItem(Date date, double value){
        this.date = date;
        this.value = value;
    }
    public DatabaseItem(CSVItem item){
        this.date = item.getDate();
        this.value = item.getClose();
    }
    private double value;
    private Date date;
    public int compareTo(Object anotherItem){
        DatabaseItem item = (DatabaseItem) anotherItem;
        return this.date.compareTo(item.getDate());
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "["+date.getTime()+":"+value+"]";
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