package server.logan_park.view.filling_report.model;

public class ManualReceiptRequest {
    private String date;
    private String time;
    private String car;
    private Double l;
    private Double price;
    private Double discount;
    private String station;
    private String shop;
    private String address;
    private Double km;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public Double getL() {
        return l;
    }

    public void setL(Double l) {
        this.l = l;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    @Override
    public String toString() {
        return "ManualReceiptRequest{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", car='" + car + '\'' +
                ", l=" + l +
                ", price=" + price +
                ", discount=" + discount +
                ", station='" + station + '\'' +
                ", shop='" + shop + '\'' +
                ", address='" + address + '\'' +
                ", km=" + km +
                '}';
    }
}
