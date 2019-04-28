package com.example.chatbot.webservicehandler;

import com.baeldung.springsoap.gen.*;
import com.example.chatbot.domain.ChatMessage;
import com.example.chatbot.domain.User;
import com.example.chatbot.domain.UserInfo;
import com.example.chatbot.repos.MessageRepo;
import com.example.chatbot.repos.UserRepo;
import com.example.imggen.IdenticonGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Endpoint
public class ChatAPIEndpoint {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";

    private final UserRepo userRepo;

    private final MessageRepo messageRepo;

    private final SimpMessagingTemplate webSocket;

    @Autowired
    public ChatAPIEndpoint(UserRepo userRepo, MessageRepo messageRepo, SimpMessagingTemplate webSocket) {
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
        this.webSocket = webSocket;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLastMessagesRequest")
    @ResponsePayload
    public GetLastMessagesResponse getLastMessagesRequest(@RequestPayload GetLastMessagesRequest request) {
        GetLastMessagesResponse response = new GetLastMessagesResponse();
        int count = request.getCount();
        List<ChatMessage> messages = messageRepo.findAllByTimestampBetween(new Date(request.getAfter()), new Date());
        messages.stream().limit(count).forEach(messageFromDb -> {
            GetLastMessagesResponse.Message message = new GetLastMessagesResponse.Message();
            message.setTimestamp(messageFromDb.getTimestamp().getTime());
            message.setUserName(messageFromDb.getSender().getUserInfo().getFirstName() + ' '
                    + messageFromDb.getSender().getUserInfo().getLastName());
            message.setData(messageFromDb.getContent());
            response.getMessage().add(message);
        });

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "loginRequest")
    @ResponsePayload
    public LoginResponse loginRequest(@RequestPayload LoginRequest request) {
        LoginResponse response = new LoginResponse();

        final String firstName = request.getFirstName();
        final String lastName = request.getLastName();
        final String password = request.getPassword();
        final String login = request.getLogin();

        User userFromDb = userRepo.findByLogin(login);

        if (userFromDb == null) {
            System.out.println("New user");
            if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
                response.setResponseCode(404);
            } else {
                User user = new User(login, password);
                UserInfo userinfo = new UserInfo(firstName, lastName);
                String avatar = IdenticonGenerator.generateImage(userinfo.toString());
                user.setAvatar(avatar);
                user.setUserInfo(userinfo);
                userinfo.setUser(user);
                user.setLoggedIn(true);
                userRepo.save(user);
                response.setResponseCode(200);
            }
        } else {
            if (userFromDb.getPassword().equals(password)) {
                userFromDb.setLoggedIn(true);
                userRepo.save(userFromDb);
                response.setResponseCode(200);
            } else {
                response.setResponseCode(404);
            }
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "logoutRequest")
    @ResponsePayload
    public LoginResponse logoutRequest(@RequestPayload LogoutRequest request) {
        LoginResponse response = new LoginResponse();

        final String password = request.getPassword();
        final String login = request.getLogin();

        User userFromDb = userRepo.findByLogin(login);

        if (userFromDb != null && userFromDb.getPassword().equals(password)) {
            userFromDb.setLoggedIn(false);
            userRepo.save(userFromDb);
            response.setResponseCode(200);
        } else {
            response.setResponseCode(404);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sendMessageRequest")
    @ResponsePayload
    public SendMessageResponse sendMessageRequest(@RequestPayload SendMessageRequest request) {
        SendMessageResponse response = new SendMessageResponse();

        final String text = request.getMessage();
        final String login = request.getLogin();

        User userFromDb = userRepo.findByLogin(login);

        if (userFromDb != null && userFromDb.isLoggedIn()) {
            ChatMessage message = new ChatMessage();
            message.setContent(text);
            message.setSender(userFromDb);
            webSocket.convertAndSend("/topic/public", message);

            message.setTimestamp(new Date());
            messageRepo.save(message);

            response.setResponseCode(200);
        } else {
            response.setResponseCode(404);
        }

        return response;
    }
}