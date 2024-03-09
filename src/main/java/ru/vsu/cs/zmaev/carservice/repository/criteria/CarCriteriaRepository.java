package ru.vsu.cs.zmaev.carservice.repository.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.entity.*;
import ru.vsu.cs.zmaev.carservice.domain.enums.CarType;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchAttributeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CarCriteriaRepository extends AbstractCriteriaRepository<Car, CarCriteriaSearch> {

    protected CarCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Car> getEntityClass() {
        return Car.class;
    }

    @Override
    protected Predicate getPredicate(CarCriteriaSearch searchCriteria, Root<Car> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(searchCriteria.getBrandName())) {
            Join<CarModel, Manufacturer> carModelManufacturerJoin = root.join("manufacturer", JoinType.INNER);
            Join<Manufacturer, Car> manufacturerCarJoin = carModelManufacturerJoin.join("car", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    manufacturerCarJoin.get("brandName"), "%" + searchCriteria.getBrandName() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getCarModelName())) {
            Join<Car, CarModel> customJoin = root.join("carModel", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    customJoin.get("carModelName"), "%" + searchCriteria.getCarModelName() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getEngineId())) {
            Join<Car, Engine> customJoin = root.join("engine", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    customJoin.get("id"), "%" + searchCriteria.getEngineId() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getEngineName())) {
            Join<Car, Engine> customJoin = root.join("engine", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    customJoin.get("name"), "%" + searchCriteria.getEngineName() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getEngineCapacity())) {
            Join<Car, Engine> customJoin = root.join("engine", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    customJoin.get("capacity"), "%" + searchCriteria.getEngineCapacity() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getTransmissionId())) {
            Join<Car, Transmission> customJoin = root.join("transmission", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    customJoin.get("id"), "%" + searchCriteria.getTransmissionId() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getTransmission())) {
            Join<Car, Transmission> customJoin = root.join("transmission", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    customJoin.get("transmissionType"), "%" + searchCriteria.getTransmission() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getReleaseYear())) {
            predicates.add(criteriaBuilder.equal(root.get("releaseYear"), searchCriteria.getReleaseYear()));
        }
        if (Objects.nonNull(searchCriteria.getCarType()) && !searchCriteria.getCarType().isEmpty()) {
            try {
                CarType.valueOf(searchCriteria.getCarType());
            } catch (IllegalArgumentException e) {
                throw new NoSuchAttributeException(searchCriteria.getCarType());
            }
            predicates.add(criteriaBuilder.equal(root.get("carType"), CarType.valueOf(searchCriteria.getCarType())));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected long getCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Car> countRoot = countQuery.from(Car.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
