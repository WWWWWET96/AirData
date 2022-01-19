package com.airdata.application.interfaces.api;

import com.airdata.application.AirQualityService;
import com.airdata.application.interfaces.api.dto.AirQualityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor // 생성자 생성
@RequestMapping("api/v1/air-quality")
public class AirQualityController {
    private final AirQualityService airQualityService;

    @GetMapping("/{sidoCode}")
    public AirQualityDto.GetAirQualityInfo getAirQualityInfo(@PathVariable("sidoCode") String sidoCode,
                                                             @RequestParam(value = "gu", required = false) String gu){

        return airQualityService.getAirQualityInfo(sidoCode, gu);
    }

}
