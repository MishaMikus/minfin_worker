package orm.entity.bolt.map_pinger_item;

import api_automation.bolt_map.BoltDriverStatusDataItem;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "minfin", name = "bolt_map_pinget_item")
public class MapPingerItem {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long base_id;
    @Column
    private Integer id;
    @Column
    private Long timestamp = new Date().getTime();
    @Column
    private Integer max_client_distance;
    @Column
    private Double lat;
    @Column
    private Double lng;
    @Column
    private String state;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String app_version;
    @Column
    private String code;
    @Column
    private String model;
    @Column
    private String car_reg_number;
    @Column
    private Integer city_id;
    @Column
    private String city;
    @Column
    private String company_name;
    @Column
    private Integer seconds_from_started_working;

    public MapPingerItem() {
    }

    public MapPingerItem(BoltDriverStatusDataItem boltDriverStatusDataItem) {
        id = boltDriverStatusDataItem.getId();
        max_client_distance = boltDriverStatusDataItem.getMax_client_distance();
        lat = boltDriverStatusDataItem.getLat();
        lng = boltDriverStatusDataItem.getLng();
        state = boltDriverStatusDataItem.getState();
        name = boltDriverStatusDataItem.getName();
        phone = boltDriverStatusDataItem.getPhone();
        app_version = boltDriverStatusDataItem.getApp_version();
        code = boltDriverStatusDataItem.getCode();
        model = boltDriverStatusDataItem.getModel();
        car_reg_number = boltDriverStatusDataItem.getCar_reg_number();
        city_id = boltDriverStatusDataItem.getCity_id();
        city = boltDriverStatusDataItem.getCity();
        company_name = boltDriverStatusDataItem.getCompany_name();
        seconds_from_started_working = boltDriverStatusDataItem.getSeconds_from_started_working();
    }

    @Override
    public String toString() {
        return "MapPingerItem{" +
                "base_id=" + base_id +
                ", id=" + id +
                ", timestamp=" + timestamp +
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

    public Long getBase_id() {
        return base_id;
    }

    public void setBase_id(Long base_id) {
        this.base_id = base_id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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
