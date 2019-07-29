package server.logan.park.view;

public class PaymentView {
    private String name;
    private String count;
    private String amount;
    private String cash;
    private String salary;
    private String change;


    public PaymentView(String name, String count, String amount, String cash, String salary, String change) {
        this.name = name;
        this.count = count;
        this.amount = amount;
        this.cash = cash;
        this.salary = salary;
        this.change = change;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
