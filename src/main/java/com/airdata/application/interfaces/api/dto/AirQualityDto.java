package com.airdata.application.interfaces.api.dto;

import com.airdata.application.AirQualityGrade;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

public class AirQualityDto {

    @Slf4j
    @Getter
    @Builder
    public static class GetAirQualityInfo {
        private String currentDate;
        private String sido;
        private Double sidoPm10Avg;
        private String sidoPm10AvgGrade;
        private List<GuAirQualityInfo> guList;
        private Integer totalCount;
    }

    @Getter
    public static class GuAirQualityInfo {
        private final String gu;
        private final Integer pm10;
        private final String pm10Grade;
        private final Integer pm25;
        private final String pm25Grade;
        private final Double o3;
        private final String o3Grade;
        private final Double no2;
        private final String no2Grade;
        private final Double co;
        private final String coGrade;
        private final Double so2;
        private final String so2Grade;

        /*builder사용하면 seoulcaller에서 grade까지 넣어줘야함*/

        public GuAirQualityInfo(String gu, Integer pm10, Integer pm25, Double o3, Double no2, Double co, Double so2) {
            this.gu = gu;
            this.pm10 = pm10;
            this.pm25 =pm25;
            this.o3 = o3;
            this.no2 = no2;
            this.co = co;
            this.so2 = so2;
            this.pm10Grade = AirQualityGrade.getPm10Grade(Double.valueOf(pm10));
            this.pm25Grade = AirQualityGrade.getPm25Grade(Double.valueOf(pm25));
            this.o3Grade = AirQualityGrade.getO3Grade(o3);
            this.no2Grade = AirQualityGrade.getNo2Grade(no2);
            this.coGrade = AirQualityGrade.getCoGrade(co);
            this.so2Grade = AirQualityGrade.getSo2Grade(so2);


        }

    }
}
