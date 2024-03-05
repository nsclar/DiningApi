package com.codecademy.DiningApi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor

public class Review {

    @Id
    @GeneratedValue
    private Long id;
    private String submittedBy;
    private Long restaurantId;
    private String content;
    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;
    @Enumerated(EnumType.ORDINAL)
    private ReviewStatus status;


}
