package com.airdata.air.util;

public class CityAverageGrade {
    private CityAverageGrade(){
    }//해당클래스밖에서 사용하지못하게 하려면 private생성자 만들어 클래스 내에서만 접근가능하게
    public static String pm25Grade(Double pm25){//글로벌 액세스가능하게 하려면? public static붙이면됨
        if(pm25 <= 15){ return "좋음";}
        if(pm25 <= 35){return "보통";}
        if(pm25 <= 75){return "나쁨";}
        else{return "매우나쁨";}
    }
    public static String pm10Grade(Double pm10){
        if(pm10 <= 30){return "좋음";}
        if(pm10 <= 80){return "보통";}
        if(pm10 <= 150){return "나쁨";}
        else {return "매우나쁨";}
    }
    public static String o3Grade(Double o3){
        if(o3 <= 0.030){return "좋음";}
        if(o3 <= 0.090){return "보통";}
        if(o3 <= 0.150){return "나쁨";}
        else {return "매우나쁨";}
    }
    public static String no2Grade(Double no2){
        if(no2 <= 0.030){return "좋음";}
        if(no2 <= 0.060){return "보통";}
        if(no2 <= 0.200){return "나쁨";}
        else {return "매우나쁨";}
    }
    public static String coGrade(Double co){
        if(co <= 2){return "좋음";}
        if(co <= 9){return "보통";}
        if(co <= 15){return "나쁨";}
        else {return "매우나쁨";}
    }
    public static String so2Grade(Double so2){
        if(so2 <= 0.020){return "좋음";}
        if(so2 <= 0.050){return "보통";}
        if(so2 <= 0.150){return "나쁨";}
        else {return "매우나쁨";}
    }
}
