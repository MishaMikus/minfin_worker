package ui_automation.okko;

import org.openqa.selenium.By;

import java.util.Date;

public class FillingRecord {
    //Дата:	2019-10-02 08:37:29
    private Date date;

    //Номер картки:	7825390000344935
    private String card;
    //Сума:	262.33
    private Double amount;
    //Сума знижки:	17.95
    private Double discount;
    //Сума зi знижкою:	280.28
    private Double amountAndDiscount;
    //Код контракту SAP:	24ПК-8276/19
    private String sapCode;
    //Назва АЗС:	АЗС 011 Львів ОККО-Рітейл
    private String shop;
    //Адрес АЗС:	Львівська, Львів, Дж.Вашингтона, 12
    private String address;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAmountAndDiscount() {
        return amountAndDiscount;
    }

    public void setAmountAndDiscount(Double amountAndDiscount) {
        this.amountAndDiscount = amountAndDiscount;
    }

    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "FillingRecord{" +
                "date=" + date +
                ", card='" + card + '\'' +
                ", amount=" + amount +
                ", discount=" + discount +
                ", amountAndDiscount=" + amountAndDiscount +
                ", sapCode=" + sapCode +
                ", shop='" + shop + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
