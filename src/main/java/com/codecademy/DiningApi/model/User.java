package com.codecademy.DiningApi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String city;
    private String state;
    private String zipcode;
    private Boolean peanutInterest;
    private Boolean eggInterest;
    private Boolean dairyInterest;


}
