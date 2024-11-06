package com.jim_jam.jim_jam_url_shortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Config to set up Rest template
 */
@Configuration
public class RestTemplateConfig {
    /**
     * Bean for primary Rest template
     * @return {@link RestTemplate}
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
