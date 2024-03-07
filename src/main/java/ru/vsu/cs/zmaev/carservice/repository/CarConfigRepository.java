package ru.vsu.cs.zmaev.carservice.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarConfig;

import java.util.List;

public interface CarConfigRepository extends JpaRepository<CarConfig, Long> {
    List<CarConfig> findByCarId(Long carId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CarConfig cc WHERE cc.car.id = :carId")
    void deleteByCarId(Long carId);
}
