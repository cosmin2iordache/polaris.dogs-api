package com.polarissoftware.dogs_api.specification;

import com.polarissoftware.dogs_api.entity.Dog;
import com.polarissoftware.dogs_api.entity.Supplier;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class DogSpecification {
    public static Specification<Dog> notDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }


    public static Specification<Dog> fromFilter(Map<String, String> filters) {
        return (root, query, cb) -> {
            var conjunction = cb.conjunction();


            if (filters == null) return conjunction;


            String name = filters.get("name");
            if (name != null && !name.isBlank()) {
                conjunction = cb.and(conjunction, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }


            String breed = filters.get("breed");
            if (breed != null && !breed.isBlank()) {
                conjunction = cb.and(conjunction, cb.like(cb.lower(root.get("breed")), "%" + breed.toLowerCase() + "%"));
            }


            String supplier = filters.get("supplier");
            if (supplier != null && !supplier.isBlank()) {
                Join<Dog, Supplier> join = root.join("supplier");
                conjunction = cb.and(conjunction, cb.like(cb.lower(join.get("name")), "%" + supplier.toLowerCase() + "%"));
            }


            return conjunction;
        };
    }
}
