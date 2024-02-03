package ru.vsu.cs.zmaev.carservice.repository.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.CarModelCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.entity.CarModel;
import ru.vsu.cs.zmaev.carservice.domain.entity.Manufacturer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CarModelCriteriaRepository extends AbstractCriteriaRepository<CarModel, CarModelCriteriaSearch> {

    protected CarModelCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<CarModel> getEntityClass() {
        return CarModel.class;
    }

    @Override
    protected Predicate getPredicate(CarModelCriteriaSearch searchCriteria, Root<CarModel> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(searchCriteria.getModelName())) {
            predicates.add(criteriaBuilder.like(root.get("modelName"), "%" + searchCriteria.getModelName() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getManufacturerName())) {
            Join<CarModel, Manufacturer> customJoin = root.join("manufacturer", JoinType.INNER);
            predicates.add(criteriaBuilder.like(
                    customJoin.get("brandName"), "%" + searchCriteria.getManufacturerName() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected long getCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<CarModel> countRoot = countQuery.from(CarModel.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
