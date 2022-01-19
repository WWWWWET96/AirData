package com.airdata.application;

import com.airdata.application.infrastructure.api.busan.BusanCaller;
import com.airdata.application.infrastructure.api.seoul.SeoulCaller;
import com.airdata.application.interfaces.api.dto.AirQualityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirQualityService {
    private final SeoulCaller seoulCaller;
    private final BusanCaller busanCaller;

    public AirQualityDto.GetAirQualityInfo getAirQualityInfo(String sido, String gu){
        if(sido == "seoul"){
            var airQualityInfo = seoulCaller.getAirData();

            return airQualityInfo;
        }

        throw new RuntimeException(sido + "대기질 정보는 아직 준비중입니다.");
    }


}
