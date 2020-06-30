package com.example.hotel.po;

/**
 * @author cynyard
 */
public class Vip {
  private Integer userId;
  private Integer vipType;
  private String registerDate;
  private String companyName;
  private String birthday;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getVipType() {
    return vipType;
  }

  public void setVipType(Integer vipType) {
    this.vipType = vipType;
  }

  public String getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(String registerDate) {
    this.registerDate = registerDate;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }
}
