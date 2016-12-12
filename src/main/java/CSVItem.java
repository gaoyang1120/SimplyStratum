import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVItem implements Comparable {
    private Date date;
    private double open;
    private double close;
    private double high;
    private double low;

    public CSVItem(Date date, double open, double close, double high, double low) {
        this.date = date;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
    }
    public CSVItem(String fileLine) {
        String[] splittedStringData = fileLine.split("\t");
        if(splittedStringData.length<6) {
            date = null;
        }else{
            String strDate = splittedStringData[0];
            String strTime = splittedStringData[1];
            String strOpen = splittedStringData[2];
            String strHigh = splittedStringData[3];
            String strLow = splittedStringData[4];
            String strClose = splittedStringData[5];
            try{
                DateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
                date = format.parse(strDate+" "+strTime);
                open = Double.parseDouble(strOpen);
                close = Double.parseDouble(strClose);
                high = Double.parseDouble(strHigh);
                close = Double.parseDouble(strClose);
            }catch(Exception e){
                date = null;
            }
        }
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVItem csvItem = (CSVItem) o;

        return date.equals(csvItem.date);

    }
    @Override
    public int hashCode() {
        return (int) date.getTime();
    }

    @Override
    public String toString() {
        return "CSVItem{" +
                "date=" + date +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
    public int compareTo(Object anotherItem){
        CSVItem item = (CSVItem) anotherItem;
        return this.date.compareTo(item.getDate());
    }
}