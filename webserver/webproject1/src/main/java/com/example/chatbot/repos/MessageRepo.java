package com.example.chatbot.repos;

import com.example.chatbot.domain.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface MessageRepo extends CrudRepository<ChatMessage, Integer> {
    List<ChatMessage> findAllByTimestampBetween(Date timeStart, Date timeEnd);

}

