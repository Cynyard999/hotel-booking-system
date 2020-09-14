package com.example.hotel.po;

/**
 * @author cynyard
 */
public class Hotel {
    private Integer id;
    private String hotelName;
    private String address;
    /**
     * 商圈
     */
    private String bizRegion;
    private String hotelStar;
    private Double rate;
    private String description;
    private String detail;
    private String phoneNum;
    /**
     * 酒店管理员id
     */
    private Integer managerId;
    private Integer evaluatorNum;

    public Integer getEvaluatorNum() {
        return evaluatorNum;
    }

    public void setEvaluatorNum(Integer evaluatorNum) {
        this.evaluatorNum = evaluatorNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBizRegion() {
        return bizRegion;
    }

    public void setBizRegion(String bizRegion) {
        this.bizRegion = bizRegion;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(String hotelStar) {
        this.hotelStar = hotelStar;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail(){return detail;}

    public void setDetail(String detail){this.detail = detail;}

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }
}

