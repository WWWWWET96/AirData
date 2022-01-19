package com.airdata;


import com.airdata.application.infrastructure.api.seoul.SeoulCaller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AirdataApplicationTests {
    @Autowired
    SeoulCaller seoulCaller;

    @Test
    void seoulcall(){
        var response = seoulCaller.getAirData();
        assertNotNull(response);

    }



}
