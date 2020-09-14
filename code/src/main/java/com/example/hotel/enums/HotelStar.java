package com.example.hotel.enums;

public enum HotelStar {
    Five("五星级"),
    Four("四星级"),
    Three("三星级");


    private String value;

    HotelStar(String value) {
        this.value = value;
    }

    public static HotelStar format(String value){
        switch (value) {
            case "五星级":
                return Five;
            case "四星级":
                return Four;
            case "三星级":
                return Three;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
