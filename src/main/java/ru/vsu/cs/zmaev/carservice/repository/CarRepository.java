package ru.vsu.cs.zmaev.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.zmaev.carservice.domain.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
