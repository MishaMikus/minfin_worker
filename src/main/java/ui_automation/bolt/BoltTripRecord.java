package ui_automation.bolt;

import java.util.Date;

public class BoltTripRecord {
    //"Номер рахунку","Дата","Водій","Адреса посадки","Спосіб оплати","Дата поїздки",
    // "Одержувач","Адреса одержувача","Реєстраційний номер одержувача","NIM / ПДВ номер одержувача",
    // "Назва компанії (водія)",
    // "Адреса компанії (вулиця, номер, індекс, країна)",
    // "Реєстраційний номер компанії",
    // "NIM / ПДВ номер компанії",
    // "Ціна (без ПДВ)",
    // "ПДВ",
    // "Загальна вартість"
    //"15703008846570","05.10.2019 21:41","Юрій Горбатий","Kniazia Sviatoslava Square 5, L'viv","Платежі Bolt","05.10.2019 21:28","Illia","","","","*","","","","66","0","66"
    private Long billNumber;
    private Date date;
    private String driver;
    private String startAddress;
    private String payMethod;
    private Date travelDate;
    private String receiver;
    private String receiverAddress;
    private String receiverNumber;
    private String taxNumber;
    private String driverCompanyName;
    private String driverCompanyAddress;
    private String driverCompanyNumber;
    private String driverCompanyTaxNumber;
    private Integer price;
    private Integer tax;
    private Integer amount;

    @Override
    public String toString() {
        return "BoltTripRecord{" +
                "billNumber=" + billNumber +
                ", date=" + date +
                ", driver='" + driver + '\'' +
                ", startAddress='" + startAddress + '\'' +
                ", payMethod='" + payMethod + '\'' +
                ", travelDate=" + travelDate +
                ", receiver='" + receiver + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverNumber='" + receiverNumber + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", driverCompanyName='" + driverCompanyName + '\'' +
                ", driverCompanyAddress='" + driverCompanyAddress + '\'' +
                ", driverCompanyNumber='" + driverCompanyNumber + '\'' +
                ", driverCompanyTaxNumber='" + driverCompanyTaxNumber + '\'' +
                ", price=" + price +
                ", tax=" + tax +
                ", amount=" + amount +
                '}';
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(Long billNumber) {
        this.billNumber = billNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getDriverCompanyName() {
        return driverCompanyName;
    }

    public void setDriverCompanyName(String driverCompanyName) {
        this.driverCompanyName = driverCompanyName;
    }

    public String getDriverCompanyAddress() {
        return driverCompanyAddress;
    }

    public void setDriverCompanyAddress(String driverCompanyAddress) {
        this.driverCompanyAddress = driverCompanyAddress;
    }

    public String getDriverCompanyNumber() {
        return driverCompanyNumber;
    }

    public void setDriverCompanyNumber(String driverCompanyNumber) {
        this.driverCompanyNumber = driverCompanyNumber;
    }

    public String getDriverCompanyTaxNumber() {
        return driverCompanyTaxNumber;
    }

    public void setDriverCompanyTaxNumber(String driverCompanyTaxNumber) {
        this.driverCompanyTaxNumber = driverCompanyTaxNumber;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
