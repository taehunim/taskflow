package com.sparta.taskflow.timeformat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureJsonTesters
public class LocalTimeFormatTest {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void localDateTime직렬화확인() throws Exception {
        // given
        SampleDto dto = new SampleDto(LocalDateTime.now());
        
        // when
        String json = objectMapper.writeValueAsString(dto);
        
        //then
        System.out.println("json = " + json);
    }
    
    static class SampleDto{
        private final LocalDateTime localDateTime;
        
        public SampleDto(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }
    }
    
}
