package com.airdata.seoul;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;


public class SeoulDto {

    @Getter
    @Setter
    @ToString
    public static class GetSeoulData {
        @JsonProperty("DailyAverageCityAir")
        private Response response;

    }
        @Getter
        @Setter
        @ToString
        public static class Response {
            @JsonProperty("RESULT")
            private Header header;
            @JsonProperty("row")
            private List<Row> row;

            public boolean isSuccess() {
                if (Objects.equals(header.code, "INFO-000")) {
                    return true;
                }
                return false;
            }
        }

        @Getter
        @Setter
        @ToString
        public static class Header {
            @JsonProperty("CODE")
            private String code;
            @JsonProperty("MESSAGE")
            private String message;

        }


        @Getter
        @Setter
        @ToString
        public static class Row {
            @JsonProperty("MSRDT_DE")
            private String date;
            @JsonProperty("MSRRGN_NM")
            private String site;
            @JsonProperty("MSRSTE_NM")
            private String town;
            @JsonProperty("PM10")
            private Double pm10;
            @JsonProperty("PM25")
            private Double pm25;
            @JsonProperty("O3")
            private Double o3;
            @JsonProperty("NO2")
            private Double no2;
            @JsonProperty("CO")
            private Double co;
            @JsonProperty("SO2")
            private Double so2;
        }
    }


