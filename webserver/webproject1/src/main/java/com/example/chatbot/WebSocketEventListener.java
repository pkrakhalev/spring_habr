package com.example.chatbot;

import com.example.chatbot.domain.User;
import com.example.chatbot.repos.UserRepo;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
public class WebSocketEventListener {

    private final UserRepo userRepo;

    public WebSocketEventListener(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
        if(username != null) {
            System.out.println("User Disconnected : " + username);

            User user = userRepo.findByLogin(username);
            user.setLoggedIn(false);
            userRepo.save(user);
        } else {
            System.out.println("User Disconnected : ");
        }
    }
}
