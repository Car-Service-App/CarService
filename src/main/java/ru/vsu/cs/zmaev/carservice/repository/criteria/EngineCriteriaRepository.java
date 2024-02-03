package ru.vsu.cs.zmaev.carservice.repository.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.zmaev.carservice.domain.dto.criteria.EngineCriteriaSearch;
import ru.vsu.cs.zmaev.carservice.domain.entity.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EngineCriteriaRepository extends AbstractCriteriaRepository<Engine, EngineCriteriaSearch> {

    protected EngineCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Engine> getEntityClass() {
        return Engine.class;
    }

    @Override
    protected Predicate getPredicate(EngineCriteriaSearch searchCriteria, Root<Engine> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(searchCriteria.getName())) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchCriteria.getName()+ "%"));
        }
        if (Objects.nonNull(searchCriteria.getCapacity())) {
            predicates.add(criteriaBuilder.like(root.get("capacity"), "%" + searchCriteria.getCapacity()+ "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    protected long getCount() {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Engine> countRoot = countQuery.from(Engine.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
