package ru.vsu.cs.zmaev.carservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarConfig;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarJob;

@Repository
public interface CarJobRepository extends JpaRepository<CarJob, Long> {
    Page<CarJob> findAll(Pageable pageable);

    Page<CarJob> findAllByCarConfig(Pageable pageable, CarConfig carConfig);

    Page<CarJob> findAllByJobType_Id(Pageable pageable, Long jobId);
}
