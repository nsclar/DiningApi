package com.codecademy.DiningApi.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String addressLine1;
    private String city;
    private String state;
    private String zipcode;
    private String phoneNumber;
    private Float eggScore;
    private Float dairyScore;
    private Float peanutScore;
    private Float overallScore;


}
