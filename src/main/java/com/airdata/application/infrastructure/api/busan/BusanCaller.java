package com.airdata.application.infrastructure.api.busan;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Slf4j
@Component
public class BusanCaller {
    private final BusanApi busanApi;

    public BusanCaller() {
        String baseUrl = "http://apis.data.go.kr/6260000/";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        this.busanApi =retrofit.create(BusanApi.class);
    }

    public BusanDto.GetBusanAirData getAirData(){
        try {
            var call = busanApi.getAirData();
            var response = call.execute().body();

            if(response == null || response.getResponse() == null){
                throw new RuntimeException("응답값 없음");
            }
            if(response.getResponse().isSuccess()){
               
                log.info(response.toString());
                return response;
            }

            throw new RuntimeException("응답이 올바르지 않습니다.");
        } catch (IOException e) {
            throw new RuntimeException("api 에러 발생");
        }
    }
}
