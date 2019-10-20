package server.logan_park.view.filling_report.model;

import java.util.Objects;

import static server.logan_park.view.filling_report.model.DateLabel.SDF_DD_MM_YYYY_HH_MM_SS;

public class FuelCosts {
    private String car;
    private DateLabel dateLabelStart = new DateLabel(SDF_DD_MM_YYYY_HH_MM_SS);
    private DateLabel dateLabelEnd = new DateLabel(SDF_DD_MM_YYYY_HH_MM_SS);
    private Integer distance;
    private Double gasAmount;
    private Double cost;

    @Override
    public String toString() {
        return "FuelCosts{" +
                "car='" + car + '\'' +
                ", dateLabelStart=" + dateLabelStart +
                ", dateLabelEnd=" + dateLabelEnd +
                ", distance=" + distance +
                ", gasAmount=" + gasAmount +
                ", cost=" + cost +
                '}';
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public DateLabel getDateLabelStart() {
        return dateLabelStart;
    }

    public void setDateLabelStart(DateLabel dateLabelStart) {
        this.dateLabelStart = dateLabelStart;
    }

    public DateLabel getDateLabelEnd() {
        return dateLabelEnd;
    }

    public void setDateLabelEnd(DateLabel dateLabelEnd) {
        this.dateLabelEnd = dateLabelEnd;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Double getGasAmount() {
        return gasAmount;
    }

    public void setGasAmount(Double gasAmount) {
        this.gasAmount = gasAmount;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuelCosts fuelCosts = (FuelCosts) o;
        return Objects.equals(car, fuelCosts.car) &&
                Objects.equals(dateLabelStart, fuelCosts.dateLabelStart) &&
                Objects.equals(dateLabelEnd, fuelCosts.dateLabelEnd) &&
                Objects.equals(distance, fuelCosts.distance) &&
                Objects.equals(gasAmount, fuelCosts.gasAmount) &&
                Objects.equals(cost, fuelCosts.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, dateLabelStart, dateLabelEnd, distance, gasAmount, cost);
    }
}
