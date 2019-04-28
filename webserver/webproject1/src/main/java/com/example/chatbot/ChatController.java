package com.example.chatbot;

import com.example.chatbot.domain.ChatMessage;
import com.example.chatbot.domain.User;
import com.example.chatbot.repos.MessageRepo;
import com.example.chatbot.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.Objects;

@Controller
public class ChatController {

    private final MessageRepo messageRepo;

    private final UserRepo userRepo;

    @Autowired
    public ChatController(MessageRepo messageRepo, UserRepo userRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("sendMessage");
        User user = chatMessage.getSender();
        User userFromDb = userRepo.findByLogin(user.getLogin());

        if (userFromDb != null && userFromDb.getPassword().equals(user.getPassword())) {

            Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", user.getLogin());

            chatMessage.setSender(userFromDb);
            chatMessage.setTimestamp(new Date());
            messageRepo.save(chatMessage);
            return chatMessage;
        } else {
            System.out.println("Unable to recognize the user");
        }

        return null;
    }

//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage,
//                               SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//    }

}