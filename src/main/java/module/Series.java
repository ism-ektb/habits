package module;

import java.time.LocalDate;

public class Series {
    private int sizeOfSeries;
    private LocalDate startSeries;

    public Series(int sizeOfSeries, LocalDate startSeries) {
        this.sizeOfSeries = sizeOfSeries;
        this.startSeries = startSeries;
    }

    public int getSizeOfSeries() {
        return sizeOfSeries;
    }

    public void setSizeOfSeries(int sizeOfSeries) {
        this.sizeOfSeries = sizeOfSeries;
    }

    public LocalDate getStartSeries() {
        return startSeries;
    }

    public void setStartSeries(LocalDate startSeries) {
        this.startSeries = startSeries;
    }

    @Override
    public String toString() {
        return "Series{" +
                "sizeOfSeries=" + sizeOfSeries +
                ", startSeries=" + startSeries +
                '}';
    }
}
