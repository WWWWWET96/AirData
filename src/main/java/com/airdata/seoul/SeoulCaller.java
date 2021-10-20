package com.airdata.seoul;

import com.airdata.air.AirDto;
import com.airdata.air.util.CityAverageGrade;
import com.airdata.air.util.CurrentDate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SeoulCaller {
    private final SeoulApi seoulApi;

    public SeoulCaller(@Value("${api.seoul.baseUrl}") String baseUrl){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        /*objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
         : JSON에는 있지만 Mapping될 Object에는 없는 필드를 무시해야하는 경우

        Retrofit : HTTP REST API 구현을 위한 라이브러리
        addConverterFactory : 응답데이터변환
        */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.seoulApi = retrofit.create(SeoulApi.class);

    }

    public AirDto.GetAirData getAirData(){
        try{
            var call = seoulApi.getAirData("20211011");
            var response = call.execute().body();

            if(response == null || response.getResponse() == null){
                throw new RuntimeException("응답 데이터 오류 발생");
                /*RuntimeException : 실행 중 발생. 시스템환경적으로나 인풋 값 잘못된 경우, 혹은 의도적으로 프로그래머가 잡아내기 위한 조건
                등 부합할 때 발생되게 만듦*/
            }
            if(response.getResponse().isSuccess()) { // true 제대로 응답됐으면
                log.info("제대로 연결됐습니다.");
                log.info(response.toString());
                return convert(response);
            }

            throw new RuntimeException("응답이 올바르지 않습니다.");//그 외 경우
        }

        catch (IOException e) {
            throw new RuntimeException("api 에러 발생");


        }
    }

    private AirDto.GetAirData convert(SeoulDto.GetSeoulData response) {
        var rows = response.getResponse().getRow();
        var items = convert(rows);
        var cityAverage = items.stream().collect(Collectors.averagingDouble(item -> item.getPm10()));
        var result = AirDto.Result.builder()
                .todayDate(CurrentDate.currentDate())
                .city("서울시")
                .cityAverage(cityAverage)
                .cityAveragaGrade(CityAverageGrade.pm10Grade(cityAverage))
                .items(items)
                .build();
        var totalCount = items.size();
        return new AirDto.GetAirData(result, totalCount);

    }
    private List<AirDto.Item> convert(List<SeoulDto.Row> rows){
        return rows.stream().map(row -> AirDto.Item.builder()
                .town(row.getTown())
                .pm25(row.getPm25())
                .pm25Grade(CityAverageGrade.pm25Grade(row.getPm25()))
                .pm10(row.getPm10())
                .pm10Grade(CityAverageGrade.pm10Grade(row.getPm10()))
                .o3(row.getO3())
                .o3Grade(CityAverageGrade.o3Grade(row.getO3()))
                .no2(row.getNo2())
                .no2Grade(CityAverageGrade.no2Grade(row.getNo2()))
                .co(row.getCo())
                .coGrade(CityAverageGrade.coGrade(row.getCo()))
                .so2(row.getSo2())
                .so2Grade(CityAverageGrade.so2Grade(row.getSo2()))
                .build()).collect(Collectors.toList());

    }


}
