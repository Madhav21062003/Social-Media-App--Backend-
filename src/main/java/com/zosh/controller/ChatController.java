package com.zosh.controller;

import com.zosh.models.Chat;
import com.zosh.models.User;
import com.zosh.request.CreateChatRequest;
import com.zosh.service.ChatService;
import com.zosh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/chats")
    public Chat createChat(@RequestHeader("Authorization")String jwt, @RequestBody CreateChatRequest request) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        User user2 = userService.findUserById(request.getUserId());
        Chat chat = chatService.createChat(reqUser, user2);

        return chat;
    }

    @GetMapping("/api/chats")
    public List<Chat> findUsersChat(@RequestHeader("Authorization")String jwt){
        User reqUser = userService.findUserByJwt(jwt);
        List<Chat> chats = chatService.findUsersChat(reqUser.getId());

        return chats;
    }
}
