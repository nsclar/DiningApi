package com.codecademy.DiningApi.controller;

import com.codecademy.DiningApi.model.AdminReview;
import com.codecademy.DiningApi.model.Restaurant;
import com.codecademy.DiningApi.model.Review;
import com.codecademy.DiningApi.model.ReviewStatus;
import com.codecademy.DiningApi.repository.RestaurantRepository;
import com.codecademy.DiningApi.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@RestController
public class AdminController {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminController(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository){
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PutMapping("/{reviewId}")
    public Review evaluateReview(@PathVariable Long reviewId, @RequestBody Review review){
        Optional<Review> getReviewOptional = reviewRepository.findById(reviewId);
        if(getReviewOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
     Optional<Restaurant> getRestaurantOptional = restaurantRepository.findRestaurantById(review.getRestaurantId());
        if(getRestaurantOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Review evaluatedReview = getReviewOptional.get();
        if(review.getStatus() == ReviewStatus.ACCEPTED) {
            evaluatedReview.setStatus(ReviewStatus.ACCEPTED);
        } else {
            evaluatedReview.setStatus(ReviewStatus.REJECTED);
        }
        this.reviewRepository.save(evaluatedReview);

        //updateRestaurantScore(getRestaurantOptional.get());
        return evaluatedReview;
    }
    @GetMapping("/pending")
    public Iterable<Review> getPendingReviews(){
        Iterable<Review> pendingReviews = this.reviewRepository.findReviewByStatus(ReviewStatus.PENDING);
        return pendingReviews;
    }

    private void updateRestaurantScore(Restaurant restaurant){
        List<Review> reviews = this.reviewRepository.findReviewByStatusAndRestaurantId(ReviewStatus.ACCEPTED, restaurant.getId());
        if(reviews.size() == 0) throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);

        int peanutCount = 0;
        int peanutSum = 0;
        int dairyCount = 0;
        int dairySum = 0;
        int eggCount = 0;
        int eggSum = 0;


        for (Review review : reviews) {
            if(!ObjectUtils.isEmpty(review.getPeanutScore())){
                peanutSum += review.getPeanutScore();
                peanutCount++;
            }
            if(!ObjectUtils.isEmpty(review.getDairyScore())){
                dairySum += review.getDairyScore();
                dairyCount++;
            }
            if(!ObjectUtils.isEmpty(review.getEggScore())){
                eggSum += review.getEggScore();
                eggCount++;
            }
            }
        int totalSum = peanutSum + dairySum + eggSum;
        int totalCount = peanutCount + dairyCount + eggCount;

        float overallScore = (float) totalSum / totalCount;
        restaurant.setOverallScore(overallScore);

        if(peanutCount > 0){
            float peanutScore = (float) peanutSum / peanutCount;
            restaurant.setPeanutScore(peanutScore);
        }
        if(dairyCount > 0){
            float dairyScore = (float) dairySum / dairyCount;
            restaurant.setDairyScore(dairyScore);
        }
        if(eggCount > 0){
            float eggScore = (float) eggSum / eggCount;
            restaurant.setEggScore(eggScore);
        }
        this.restaurantRepository.save(restaurant);



    }
}
