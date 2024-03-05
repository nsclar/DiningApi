package com.codecademy.DiningApi.repository;

import com.codecademy.DiningApi.model.Review;
import com.codecademy.DiningApi.model.ReviewStatus;
import org.apache.catalina.valves.rewrite.RewriteCond;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {

        List<Review> findReviewByStatus(ReviewStatus status);
        List<Review> findReviewByStatusAndRestaurantId(ReviewStatus status, Long restaurantId);

}
