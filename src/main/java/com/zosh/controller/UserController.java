package com.zosh.controller;

import com.zosh.exceptions.UserException;
import com.zosh.models.User;
import com.zosh.repository.UserRepository;
import com.zosh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    // Create User into The database


    // Now Get all users from the database
    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();

        return allUsers;
    }

    // Now Get User by id
    @GetMapping("/api/users/{userId}")
    public User getUserById(@PathVariable("userId") Integer id) throws UserException {

        return userService.findUserById(id);
    }

    @PutMapping("/api/users")
    public User updateUser(@RequestHeader("Authorization")String jwt ,@RequestBody User user) throws UserException{
        User reqUser = userService.findUserByJwt(jwt);
        return userService.updateUser(user, reqUser.getId());
    }


    @PutMapping("/api/users/follow/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization") String jwt,@PathVariable Integer userId2) throws UserException{

        User reqUser = userService.findUserByJwt(jwt);
        User user = userService.followUser(reqUser.getId(),userId2);
        return user;
    }

    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam ("query") String query){
        List<User> users = userService.searchUser(query);

        return users;
    }

    @DeleteMapping("/api/users/{userId}")
    public String deleteUser(@PathVariable("userId") Integer id)throws UserException    {

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()){
            throw new UserException("User does not exist with the given id");
        }
        userRepository.delete(user.get());
        return "user deleted successfully";
    }

    @GetMapping("/api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization")String jwt){

        User user = userService.findUserByJwt(jwt);
        System.out.println("======================================================");
        System.out.println(user.getId());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        System.out.println("JWT Token -> "+jwt);
        System.out.println("======================================================");


        return user;
    }
}
