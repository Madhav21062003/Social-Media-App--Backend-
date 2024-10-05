package com.zosh.controller;

import com.zosh.models.Story;
import com.zosh.models.User;
import com.zosh.service.StoryService;
import com.zosh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryController {


    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/story")
    public Story createStory(@RequestBody Story story, @RequestHeader("Authorization")String jwt){
        User reqUser = userService.findUserByJwt(jwt);

        Story createdstory = storyService.createStory(story, reqUser);

        return createdstory;
    }

    @GetMapping("/api/story/user/{userId}")
    public List<Story> findUserStory(@PathVariable("userId")Integer userId,@RequestHeader("Authorization")String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);

        List<Story> stories = storyService.findStoryByUserId(userId);

        return stories;
    }

}
