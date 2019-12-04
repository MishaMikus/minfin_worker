package api_automation.bolt_map;

public class BoltDriverStatusDataItem {
//    "id": 1212885,
//            "max_client_distance": 2,
//            "lat": 49.8316662,
//            "lng": 24.0168457,
//            "state": "has_order",
//            "name": "Віталій Антропов",
//            "phone": "+380935947494",
//            "app_version": "DA.4.31",
//            "code": "",
//            "model": "Dacia Logan",
//            "car_reg_number": "BC4275II",
//            "city_id": 496,
//            "city": "Lviv",
//            "company_name": "",
//            "seconds_from_started_working": 1937

    private Integer id;
    private Integer max_client_distance;
    private Double lat;
    private Double lng;
    private String state;
    private String name;
    private String phone;
    private String app_version;
    private String code;
    private String model;
    private String car_reg_number;
    private Integer city_id;
    private String city;
    private String company_name;
    private Integer seconds_from_started_working;

    @Override
    public String toString() {
        return "BoltDriverStatusDataItem{" +
                "id=" + id +
                ", max_client_distance=" + max_client_distance +
                ", lat=" + lat +
                ", lng=" + lng +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", app_version='" + app_version + '\'' +
                ", code='" + code + '\'' +
                ", model='" + model + '\'' +
                ", car_reg_number='" + car_reg_number + '\'' +
                ", city_id=" + city_id +
                ", city='" + city + '\'' +
                ", company_name='" + company_name + '\'' +
                ", seconds_from_started_working='" + seconds_from_started_working + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMax_client_distance() {
        return max_client_distance;
    }

    public void setMax_client_distance(Integer max_client_distance) {
        this.max_client_distance = max_client_distance;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCar_reg_number() {
        return car_reg_number;
    }

    public void setCar_reg_number(String car_reg_number) {
        this.car_reg_number = car_reg_number;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Integer getSeconds_from_started_working() {
        return seconds_from_started_working;
    }

    public void setSeconds_from_started_working(Integer seconds_from_started_working) {
        this.seconds_from_started_working = seconds_from_started_working;
    }
}
