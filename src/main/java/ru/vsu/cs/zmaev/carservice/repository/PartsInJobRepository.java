package ru.vsu.cs.zmaev.carservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarJob;
import ru.vsu.cs.zmaev.carservice.domain.entity.PartsInJob;

import java.util.Optional;

@Repository
public interface PartsInJobRepository extends JpaRepository<PartsInJob, Long> {
    Page<PartsInJob> findAllByCarJobJobType_Id(Pageable pageable, Long jobId);

    Optional<PartsInJob> findByCarPartIdAndCarJob(Long carPartId, CarJob carJob);

}
