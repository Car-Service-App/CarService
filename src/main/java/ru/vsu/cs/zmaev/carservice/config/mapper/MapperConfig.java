package ru.vsu.cs.zmaev.carservice.config.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarConfigMapper;
import ru.vsu.cs.zmaev.carservice.domain.mapper.CarConfigMapperImpl;

@Configuration
public class MapperConfig {
    @Bean
    public CarConfigMapper carConfigMapper() {
        return new CarConfigMapperImpl();
    }
}
