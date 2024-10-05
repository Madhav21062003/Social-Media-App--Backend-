package com.zosh.service;

import com.zosh.models.Chat;
import com.zosh.models.Message;
import com.zosh.models.User;

import java.util.List;

public interface MessageService {

    public Message createMessage(User user, Integer chatId, Message req) throws Exception;

    public List<Message> findChatsMessages(Integer chatId) throws Exception;

}
