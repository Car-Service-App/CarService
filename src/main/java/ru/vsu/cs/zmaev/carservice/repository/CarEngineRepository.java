package ru.vsu.cs.zmaev.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarEngine;

public interface CarEngineRepository extends JpaRepository<CarEngine, Long> {
}
