package com.example.hotel.vo;

/**
 * @author cynyard
 */
public class VipVO {
  private Integer vipType;
  private String registerDate;
  private Integer userId;

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

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }
}
