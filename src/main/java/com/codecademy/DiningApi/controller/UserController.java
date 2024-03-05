package com.codecademy.DiningApi.controller;

import com.codecademy.DiningApi.model.User;
import com.codecademy.DiningApi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.ObjectUtils;


import javax.swing.text.html.Option;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    public final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public User createNewUser(@RequestBody User newUser) {
        validateUser(newUser);
        this.userRepository.save(newUser);
        return newUser;

    }

    @GetMapping("/{userName}")
    public User getByUserName(@PathVariable String userName) {
        Optional<User> userNameToGetOptional = this.userRepository.findUserByUserName(userName);
        if (userNameToGetOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with that Username");
        }
        User userNameToGet = userNameToGetOptional.get();
        return userNameToGet;

    }
    @GetMapping("/")
    public Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
            }

    @PutMapping("/{userName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable String userName, @RequestBody User updatedUser) {
        validateUserName(userName);
        Optional<User> userToUpdateOptional = this.userRepository.findUserByUserName(userName);
        if(userToUpdateOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist");
        }
        User userToUpdate = userToUpdateOptional.get();
        updateUserFields(updatedUser, userToUpdate);
        this.userRepository.save(userToUpdate);
    }

    public void updateUserFields(User newUser, User exsistingUser) {

        //This was throwing the exception every time that userName was not present in the requestBody.
        /* if (ObjectUtils.isEmpty(newUser.getUserName())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } */

        if(!ObjectUtils.isEmpty(newUser.getCity())){
            exsistingUser.setCity(newUser.getCity());
        }
        if(!ObjectUtils.isEmpty(newUser.getState())){
            exsistingUser.setState(newUser.getState());
        }
        if(!ObjectUtils.isEmpty(newUser.getZipcode())){
            exsistingUser.setZipcode(newUser.getZipcode());
        }
        if(!ObjectUtils.isEmpty(newUser.getEggInterest())){
            exsistingUser.setEggInterest(newUser.getEggInterest());
        }
        if(!ObjectUtils.isEmpty(newUser.getDairyInterest())){
            exsistingUser.setDairyInterest(newUser.getDairyInterest());
        }
        if(!ObjectUtils.isEmpty(newUser.getPeanutInterest())){
            exsistingUser.setPeanutInterest(newUser.getPeanutInterest());
        }
    }
    private void validateUser(User user) {
        validateUserName(user.getUserName());
        Optional<User> validUserEntry = this.userRepository.findUserByUserName(user.getUserName());
        if (validUserEntry.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }
    private void validateUserName(String userName) {
        if (ObjectUtils.isEmpty(userName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}