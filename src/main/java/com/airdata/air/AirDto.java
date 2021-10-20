package com.airdata.air;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class AirDto {

    @Getter
    public static class GetAirData{
        @JsonProperty("air-info")
        private Result result;
        private Integer totalCount;

        public GetAirData(Result result, Integer totalCount) {
            this.result = result;
            this.totalCount = totalCount;
        }
    }

    @Builder
    @Getter
    public static class Result{
        private String todayDate;
        private String city;
        private Double cityAverage;
        private String cityAveragaGrade;
        private List<Item> items;
    }

    @Builder
    @Getter
    public static class Item{
        private String town;
        private Double pm25;
        private String pm25Grade;
        private Double pm10;
        private String pm10Grade;
        private Double o3;
        private String o3Grade;
        private Double no2;
        private String no2Grade;
        private Double co;
        private String coGrade;
        private Double so2;
        private String so2Grade;
    }




}
