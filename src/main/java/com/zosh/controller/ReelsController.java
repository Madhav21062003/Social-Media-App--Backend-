package com.zosh.controller;

import com.zosh.models.Reels;
import com.zosh.models.User;
import com.zosh.service.ReelsService;
import com.zosh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelsController  {

    @Autowired
    private ReelsService reelsService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody Reels reel, @RequestHeader("Authorization") String jwt){

        User reqUser = userService.findUserByJwt(jwt);

        Reels createdReels = reelsService.createReel(reel, reqUser);

        return  createdReels;
    }


    @GetMapping("/api/reels")
    public List<Reels> findAllReels(){

        List<Reels> allReels = reelsService.findAllReels();
        return  allReels;
    }

    @GetMapping("/api/reels/user/{userId}")
    public List<Reels> findUsersReels(@PathVariable Integer userId) throws Exception {

        List<Reels> reels = reelsService.findUsersReel(userId);
        return  reels;
    }

}
