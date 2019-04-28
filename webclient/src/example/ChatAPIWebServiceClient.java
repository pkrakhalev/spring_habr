package example;


import example.wsdl.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatAPIWebServiceClient {

    private static final String LOGIN_NAME = "HabrBot";
    private static final long CHECK_NEW_HABR_REQUEST_TIMEOUT = 5;

    public static void main(String[] args) {
        ChatPortService service = new ChatPortService();
        ChatPort port = service.getChatPortSoap11();

        login(port);

        Set<Long> responded = new HashSet<>();

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            List<GetLastMessagesResponse.Message> messages = getMessages(port);
            messages.forEach(message -> {
                if (message.getData().equals("/habr") || message.getData().equals("!habr")) {
                    Long timeStamp = message.getTimestamp();
                    if (!responded.contains(timeStamp)) {
                        responded.add(timeStamp);
                        sendMessage(port);
                    }
                }
            });

        }, 0, CHECK_NEW_HABR_REQUEST_TIMEOUT, TimeUnit.SECONDS);
    }

    private static void login(ChatPort port) {
        LoginRequest request = new LoginRequest();
        request.setFirstName("Habr");
        request.setLastName("Bot");
        request.setLogin(LOGIN_NAME);
        request.setPassword("11111");

        LoginResponse response = port.login(request);
        if (response.getResponseCode() == 200) {
            System.out.println("Bot is logged in");
        } else {
            System.out.println("Error during log in");
            System.exit(1);
        }
    }

    private static List<GetLastMessagesResponse.Message> getMessages(ChatPort port) {
        GetLastMessagesRequest getLastMessagesRequest = new GetLastMessagesRequest();
        getLastMessagesRequest.setCount(100);

        LocalDateTime localDateTime = LocalDateTime.now().minusSeconds(10);
        Date out = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        getLastMessagesRequest.setAfter(out.getTime());

        GetLastMessagesResponse response = port.getLastMessages(getLastMessagesRequest);
        return response.getMessage();
    }
    private static void sendMessage(ChatPort port) {
        SendMessageRequest request = new SendMessageRequest();
        request.setLogin(LOGIN_NAME);
        HabrLinkGenerator gen = new HabrLinkGenerator();
        try {
            request.setMessage(gen.getRandomHabrLink());
        } catch (IOException e) {
            System.out.println("Unable to get habr link");
            return;
        }

        SendMessageResponse response = port.sendMessage(request);
        if (response.getResponseCode() == 200) {
            System.out.println("Message is sent");
        } else {
            System.out.println("Error during send message");
            System.exit(1);
        }
    }
}