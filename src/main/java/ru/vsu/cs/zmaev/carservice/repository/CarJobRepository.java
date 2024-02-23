package ru.vsu.cs.zmaev.carservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarJob;

@Repository
public interface CarJobRepository extends JpaRepository<CarJob, Long> {
    Page<CarJob> findAll(Pageable pageable);

    Page<CarJob> findAllByJobType_Id(Pageable pageable, Long jobId);

//    @Query("SELECT new CarJob (cj.id, jt, cr) " +
//            "FROM CarJob cj " +
//            "JOIN cj.jobType jt " +
//            "JOIN cj.carEngine ce " +
//            "JOIN ce cr " +
//            "WHERE cj.id = :carJobId")
//    List<CarJobResponseDto> findCarJobResponseDtoById(@Param("carJobId") Long carJobId);
//
//    @Query("SELECT new ru.vsu.cs.zmaev.carservice.domain.dto.response.CarJobResponseDto(cj.id, jt.id, car) " +
//            "FROM CarJob cj " +
//            "JOIN cj.jobType jt " +
//            "JOIN cj.carEngine carEngine " +
//            "JOIN carEngine car")
//    List<CarJobResponseDto> getCarJobResponseDtos();
}
