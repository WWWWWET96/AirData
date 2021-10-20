package com.airdata.busan;

import com.airdata.air.AirDto;
import com.airdata.air.util.CityAverageGrade;
import com.airdata.air.util.CurrentDate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BusanCaller {
    private final BusanApi busanApi;

    public BusanCaller(@Value("${api.busan.baseUrl}") String baseUrl) {
        //String baseUrl = "http://apis.data.go.kr/6260000/";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        this.busanApi =retrofit.create(BusanApi.class);
    }

    public AirDto.GetAirData getAirData(){
        try {
            var call = busanApi.getAirData();
            var response = call.execute().body();

            if(response == null || response.getResponse() == null){
                throw new RuntimeException("응답값 없음");
            }
            if(response.getResponse().isSuccess()){
                log.info("연결성공");
                log.info(response.toString());
                return convert(response);
            }

            throw new RuntimeException("응답이 올바르지 않습니다.");
        } catch (IOException e) {
            throw new RuntimeException("api 에러 발생");
        }
    }

    private AirDto.GetAirData convert(BusanDto.GetBusanAirData response){
        var rows = response.getResponse().getItems();
        var items = convert(rows);
        var cityAverage = items.stream().collect(Collectors.averagingDouble(item -> item.getPm10()));
        var result = AirDto.Result.builder()
                .todayDate(CurrentDate.currentDate())
                .city("부산시")
                .cityAverage(cityAverage)
                .cityAveragaGrade(CityAverageGrade.pm10Grade(cityAverage))
                .items(items)
                .build();
        var totalCount = items.size();
        return new AirDto.GetAirData(result, totalCount);

    }
    private List<AirDto.Item> convert(List<BusanDto.Item> rows){
        return rows.stream().map(row -> AirDto.Item.builder()
                .town(row.getTown())
                .pm25(Double.parseDouble(row.getPm25()))
                .pm25Grade(CityAverageGrade.pm25Grade(Double.parseDouble(row.getPm25())))
                .pm10(Double.parseDouble(row.getPm10()))
                .pm10Grade(CityAverageGrade.pm10Grade(Double.parseDouble(row.getPm10())))
                .o3(Double.parseDouble(row.getO3()))
                .o3Grade(CityAverageGrade.o3Grade(Double.parseDouble(row.getO3())))
                .no2(Double.parseDouble(row.getNo2()))
                .no2Grade(CityAverageGrade.no2Grade(Double.parseDouble(row.getNo2())))
                .co(Double.parseDouble(row.getCo()))
                .coGrade(CityAverageGrade.coGrade(Double.parseDouble(row.getCo())))
                .so2(Double.parseDouble(row.getSo2()))
                .so2Grade(CityAverageGrade.so2Grade(Double.parseDouble(row.getSo2())))
                .build()).collect(Collectors.toList());
    }
}
