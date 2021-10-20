package com.airdata.air;


import com.airdata.busan.BusanCaller;
import com.airdata.seoul.SeoulCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirService {
    private final SeoulCaller seoulCaller;
    private final BusanCaller busanCaller;
    public AirDto.GetAirData getAirResponse(String city){
        if(city.equals("서울시")){
           var response = seoulCaller.getAirData();
           return response;
        }
        else if(city.equals("부산시")){
            var response = busanCaller.getAirData();
            return response;
        }

        throw new RuntimeException("오류");
    }
}
