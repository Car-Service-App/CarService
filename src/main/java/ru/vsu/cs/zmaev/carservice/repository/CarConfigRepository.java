package ru.vsu.cs.zmaev.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarConfig;

import java.util.List;

public interface CarConfigRepository extends JpaRepository<CarConfig, Long> {
    List<CarConfig> findByCarId(Long carId);
}
