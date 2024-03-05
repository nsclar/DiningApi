package com.codecademy.DiningApi.repository;

import com.codecademy.DiningApi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Optional<Restaurant> findRestaurantByNameAndZipcode(String name, String zipcode);
    Optional<Restaurant> findRestaurantById(Long id);
    List<Restaurant> findRestaurantByZipcodeAndDairyScoreNotNullOrderByDairyScoreDesc(String zipcode);
    List<Restaurant> findRestaurantByZipcodeAndEggScoreNotNullOrderByEggScoreDesc(String zipcode);
    List<Restaurant> findRestaurantByZipcodeAndPeanutScoreNotNullOrderByPeanutScoreDesc(String zipcode);
}
