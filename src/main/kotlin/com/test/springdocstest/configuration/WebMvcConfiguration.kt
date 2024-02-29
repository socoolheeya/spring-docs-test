package com.test.springdocstest.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebMvcConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return objectMapper();
    }
}