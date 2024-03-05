package com.codecademy.DiningApi.controller;

import com.codecademy.DiningApi.model.Restaurant;
import com.codecademy.DiningApi.model.Review;
import com.codecademy.DiningApi.model.ReviewStatus;
import com.codecademy.DiningApi.model.User;
import com.codecademy.DiningApi.repository.RestaurantRepository;
import com.codecademy.DiningApi.repository.ReviewRepository;
import com.codecademy.DiningApi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping("/reviews")
@RestController
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewController(ReviewRepository reviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("{restaurantId}")
    public List<Review> getApprovedReviewsByRestaurant(@PathVariable Long restaurantId){
        List<Review> restaurantList = this.reviewRepository.findReviewByStatusAndRestaurantId(ReviewStatus.ACCEPTED, restaurantId);
        return restaurantList;
    }

    @PostMapping("/submitReview")
    @ResponseStatus(HttpStatus.CREATED)
    public Review createNewReview(@RequestBody Review review) {
        validateReview(review);


        review.setStatus(ReviewStatus.PENDING);
        this.reviewRepository.save(review);
        return review;
    }

private void validateReview(Review review){
        if(ObjectUtils.isEmpty(review.getSubmittedBy())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter a username");
        }
        if (ObjectUtils.isEmpty(review.getRestaurantId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter the restaurantId");
        }
        if(ObjectUtils.isEmpty(review.getEggScore()) && ObjectUtils.isEmpty(review.getDairyScore()) && ObjectUtils.isEmpty(review.getPeanutScore())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please enter at least one score");
        }
    Optional<User> userOptional = userRepository.findUserByUserName(review.getSubmittedBy());
    if(userOptional.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "This username does not exist in the system");
    }
    Optional<Restaurant> restaurantOptional = restaurantRepository.findRestaurantById(review.getRestaurantId());
    if(restaurantOptional.isEmpty()){
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "This restaurant does not exist");
    }
}
}
