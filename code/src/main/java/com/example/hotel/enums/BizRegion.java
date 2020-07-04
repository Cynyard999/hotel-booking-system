package com.example.hotel.enums;

public enum BizRegion {
    XinJieKou("新街口"),
    JiangNing("江宁"),
    ZhuJiangLu("珠江路"),
    FuZiMiao("夫子庙"),
    HeXi("河西"),
    XianLin("仙林"),
    JiangBei("江北");

    private String value;

    BizRegion(String value) {
        this.value = value;
    }

    /*public static BizRegion format(String value){
        switch (value) {
            case "新街口":
                return XinJieKou;
            case "江宁":
                return JiangNing;
            case "珠江路":
                return ZhuJiangLu;
            case "夫子庙":
                return FuZiMiao;
            case "河西":
                return HeXi;
            case "仙林":
                return XianLin;
            case "江北":
                return JiangBei;
            default:
                return null;
        }
    }*/

    @Override
    public String toString() {
        return value;
    }


}
