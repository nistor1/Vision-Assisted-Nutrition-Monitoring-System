package org.nutrition.app.meal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MealConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
