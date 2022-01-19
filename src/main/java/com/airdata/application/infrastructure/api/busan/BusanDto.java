package com.airdata.application.infrastructure.api.busan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

public class BusanDto {
    @Getter
    @Setter
    @ToString
    public static class GetBusanAirData{
        @JsonProperty("getAirQualityInfoClassifiedByStation")
        private Response response;
    }

    @Getter
    @Setter
    @ToString
    public static class Response{
        private Header header;
        @JsonProperty("item")
        private List<Item> items;
        private Integer numOfRows;
        private Integer pageNo;
        private Integer totalCount;

        public boolean isSuccess(){
            if(Objects.equals(header.code,"00")){return true;}
            return false;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Header{
        private String code;
        private String message;
    }

    @Getter
    @Setter
    @ToString
    public static class Item{
        @JsonProperty("site")
        private String town;
        private String areaIndex;
        @JsonProperty("controlnumber")
        private String date;
        private String repItem;
        private String repVal;
        private String repCai;
        private String so2;
        private String so2Cai;
        private String no2;
        private String no2Cai;
        private String o3;
        private String o3Cai;
        private String co;
        private String coCai;
        private String pm25;
        private String pm25Cai;
        private String pm10;
        private String pm10Cai;

    }
}