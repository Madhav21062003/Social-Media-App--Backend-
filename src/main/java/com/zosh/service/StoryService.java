package com.zosh.service;

import com.zosh.models.Story;
import com.zosh.models.User;

import java.util.List;

public interface StoryService {

    public Story createStory (Story story, User user);

    public List<Story> findStoryByUserId(Integer userId) throws Exception;
}
