package ru.vsu.cs.zmaev.carservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.cs.zmaev.carservice.domain.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findAll(Pageable pageable);

    @Query("SELECT с FROM Car с WHERE с.carModel.id = :id")
    List<Car> findCarByCarModel(Long id);

    @Query("SELECT c FROM Car c LEFT JOIN FETCH c.carConfigs WHERE c.id = :carId")
    Optional<Car> findCarWithCarConfigsById(@Param("carId") Long carId);
}
