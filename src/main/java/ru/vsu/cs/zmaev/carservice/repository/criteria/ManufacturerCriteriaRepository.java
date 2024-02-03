package ru.vsu.cs.zmaev.carservice.repository.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.ManufacturerCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.entity.Manufacturer;
import ru.vsu.cs.zmaev.carservice.domain.enums.ProductionType;
import ru.vsu.cs.zmaev.carservice.exception.NoSuchAttributeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ManufacturerCriteriaRepository extends AbstractCriteriaRepository<Manufacturer, ManufacturerCriteriaSearch> {

    protected ManufacturerCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Manufacturer> getEntityClass() {
        return Manufacturer.class;
    }

    @Override
    protected Predicate getPredicate(ManufacturerCriteriaSearch searchCriteria, Root<Manufacturer> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(searchCriteria.getBrandName())) {
            predicates.add(criteriaBuilder.like(root.get("brandName"), "%" + searchCriteria.getBrandName() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getCountry())) {
            predicates.add(criteriaBuilder.like(root.get("country"), "%" + searchCriteria.getCountry() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getProduction()) && !searchCriteria.getProduction().isEmpty()) {
            try {
                ProductionType.valueOf(searchCriteria.getProduction());
            } catch (IllegalArgumentException e) {
                throw new NoSuchAttributeException(searchCriteria.getProduction());
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected long getCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Manufacturer> countRoot = countQuery.from(Manufacturer.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
