package com.test.springdocstest.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableWebMvc
@Configuration
class MvcConfiguration: WebMvcConfigurer {

    override fun configureMessageConverters(converters: List<HttpMessageConverter<*>>) {
        converters.stream()
            .filter{ it is MappingJackson2HttpMessageConverter }
            .findFirst()
            .ifPresent {
                (it as MappingJackson2HttpMessageConverter).defaultCharset = Charsets.UTF_8
            }
    }
}