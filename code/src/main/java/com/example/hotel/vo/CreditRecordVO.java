package com.example.hotel.vo;

public class CreditRecordVO {
    private int id;
    private int userId;
    private int orderId;
    private String changeTime;
    private String changeAction;
    private double changeValue;
    private double finalValue;

    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getOrderId(){
        return orderId;
    }
    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    public String getChangeTime(){
        return changeTime;
    }
    public void setChangeTime(String changeTime){
        this.changeTime = changeTime;
    }

    public String getChangeAction(){
        return changeAction;
    }
    public void setChangeAction(String changeAction){
        this.changeAction = changeAction;
    }

    public double getChangeValue(){
        return changeValue;
    }
    public void setChangeValue(double changeValue){
        this.changeValue = changeValue;
    }

    public double getFinalValue(){
        return finalValue;
    }
    public void setFinalValue(double finalValue){
        this.finalValue = finalValue;
    }
}
