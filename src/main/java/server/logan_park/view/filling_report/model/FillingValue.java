package server.logan_park.view.filling_report.model;

public class FillingValue {
    private Double amount = 0.0;
    private Double count = 0.0;

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
}
