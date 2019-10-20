package server.logan_park.view.filling_report.model;

import java.util.ArrayList;
import java.util.List;

public class FillingValue {
    private Double amount = 0.0;
    private Double count = 0.0;
    private List<FuelCosts> fuelCostsList=new ArrayList<>();

    public List<FuelCosts> getFuelCostsList() {
        return fuelCostsList;
    }

    public void setFuelCostsList(List<FuelCosts> fuelCostsList) {
        this.fuelCostsList = fuelCostsList;
    }

    public FillingValue(Double amount, Double count) {
        this.amount=amount;
        this.count=count;
    }

    public FillingValue() {

    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "FillingValue{" +
                "amount=" + amount +
                ", count=" + count +
                ", fuelCostsList=" + fuelCostsList +
                '}';
    }
}
