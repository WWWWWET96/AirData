package com.airdata.air;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController // view반환하는 controller와달리 Json형태로 객체 데이터 반환
@RequiredArgsConstructor // final,NonNull의 생성자 생성
@RequestMapping("/v1/api")
public class AirController {
    private final AirService airService;

    @GetMapping("/air-data/{city}")
    public AirDto.GetAirData getAirData(@PathVariable("city") String city, @RequestParam(required = false) String town){
        return airService.getAirResponse(city);
    }



}