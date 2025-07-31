package com.lamashkevich.hotelmanagementsystem.repository.spec;

import com.lamashkevich.hotelmanagementsystem.dto.HotelFilter;
import com.lamashkevich.hotelmanagementsystem.entity.Address;
import com.lamashkevich.hotelmanagementsystem.entity.Hotel;
import com.lamashkevich.hotelmanagementsystem.entity.HotelAmenity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HotelSpecification {

    public static Specification<Hotel> filter(HotelFilter filter) {
        return hasName(filter.name())
                .and(hasBrand(filter.brand()))
                .and(hasCity(filter.city()))
                .and(hasCountry(filter.country()))
                .and(hasAmenities(filter.amenities()));
    }

    public static Specification<Hotel> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) -> {
            if (brand == null || brand.isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
        };
    }

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isEmpty()) return cb.conjunction();
            Join<Hotel, Address> join = root.join("address");
            return cb.like(cb.lower(join.get("city")), "%" + city.toLowerCase() + "%");
        };
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) -> {
            if (country == null || country.isEmpty()) return cb.conjunction();
            Join<Hotel, Address> join = root.join("address");
            return cb.like(cb.lower(join.get("country")), "%" + country.toLowerCase() + "%");
        };
    }

    public static Specification<Hotel> hasAmenities(List<String> amenities) {
        return (root, query, cb) -> {
            if (amenities == null || amenities.isEmpty()) return cb.conjunction();

            var amenitiesInLowerCase = amenities.stream().map(String::toLowerCase).toList();

            Join<Hotel, HotelAmenity> join = root.join("amenities", JoinType.INNER);
            Predicate predicate = cb.lower(join.get("name")).in(amenitiesInLowerCase);

            query.groupBy(root.get("id")).having(
                    cb.equal(cb.count(join.get("name")), amenitiesInLowerCase.size()));

            return predicate;
        };
    }

}
