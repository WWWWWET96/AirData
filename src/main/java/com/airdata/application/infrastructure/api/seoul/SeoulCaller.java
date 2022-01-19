package com.airdata.application.infrastructure.api.seoul;

import com.airdata.application.AirQualityGrade;
import com.airdata.application.interfaces.api.dto.AirQualityDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SeoulCaller {
    private final SeoulApi seoulApi;

    public SeoulCaller(){
        String baseUrl = "http://openAPI.seoul.go.kr:8088/";
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

    public AirQualityDto.GetAirQualityInfo getAirData(){
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

    private AirQualityDto.GetAirQualityInfo convert(SeoulDto.GetSeoulData response) {
        var rows = response.getResponse().getRows();
        var sidoPm10Avg = averagePm10(rows);
        var sidoPm10AvgGrade = AirQualityGrade.getPm10Grade(sidoPm10Avg);
        var guList = convert(rows);

        return AirQualityDto.GetAirQualityInfo.builder()
                .currentDate("20220117")
                .sido("seoul")
                .sidoPm10Avg(sidoPm10Avg)
                .sidoPm10AvgGrade(sidoPm10AvgGrade)
                .guList(guList)
                .totalCount(guList.size())
                .build();
    }

    private List<AirQualityDto.GuAirQualityInfo> convert(List<SeoulDto.Row> rows){
        return rows.stream()
                .map(row -> new AirQualityDto.GuAirQualityInfo(
                        row.getSite(),
                        row.getPm10(),
                        row.getPm25(),
                        row.getO3(),
                        row.getNo2(),
                        row.getCo(),
                        row.getSo2())

                )
                .collect(Collectors.toList());
    }
    private Double averagePm10(List<SeoulDto.Row> rows){
        return rows.stream()
                .mapToInt(SeoulDto.Row::getPm10)
                .average()
                .orElse(Double.NaN);
    }


}
