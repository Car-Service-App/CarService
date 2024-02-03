package ru.vsu.cs.zmaev.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.zmaev.carservice.domain.entity.Manufacturer;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findByBrandName(String brandName);
}
