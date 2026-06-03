package com.vp.jobportal.repository;

import com.vp.job.domain.JobStatus;
import com.vp.jobportal.payload.JobSearchRequest;
import jakarta.persistence.criteria.Path;
import lombok.*;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobSpecification {


    public static Sort buildSpecification(JobSearchRequest req) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("active")));

            JobStatus status = req.getStatus() != null ? req.getStatus() : JobStatus.OPEN;
            predicates.add(cb.equal(root.get("status"), status));

            if (req.getJobType() != null) {
                predicates.add(cb.equal(root.get("jobType"), req.getJobType()));
            }
            if(req.getWorkMode() != null){
                predicates.add(cb.equal(root.get("workMode"), req.getWorkMode()));
            }
            if(req.getExperienceLevel() != null){
                predicates.add(cb.equal(root.get("experienceLevel"), req.getExperienceLevel()));
            }
            if(req.getCompanyId() !=null){
                predicates.add(cb.equal(root.get("companyId"), req.getCompanyId()));
            }
            if(req.getCategoryId() != null){
                predicates.add(cb.equal(root.get("categoryId"), req.getCategoryId()));
            }
            if(req.getLocation() !=null && !req.getLocation().isBlank()){
                String pattern = "%" + req.getLocation() + "%";
                Path<String> city = root.get("location").get("city");
                Path<String> state = root.get("location").get("state");
                Path<String> country = root.get("location").get("country");

                predicates.add(cb.or(
                        cb.like(cb.lower(city), pattern),
                        cb.like(cb.lower(state), pattern),
                        cb.like(cb.lower(country), pattern)
                ));
            }

            if(req.getMinSalary() != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("salary"), req.getMinSalary()));
            }
            if(req.getMaxSalary() != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("salary"), req.getMaxSalary()));
            }

            if(req.getMinOpenings() != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("openings"), req.getMinOpenings()));
            }
            if(req.getMaxOpenings() != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("openings"), req.getMaxOpenings()));
            }

            //todo : filter tag skills

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
