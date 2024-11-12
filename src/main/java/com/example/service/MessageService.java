package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().length() > 255 || message.getMessageText().isEmpty()) {
            throw new IllegalArgumentException("Message should not be empty and should not be over 255 characters");
        }

        if (accountRepository.findById(message.getPostedBy()).orElse(null) == null) {
            throw new IllegalArgumentException("User does not exist.");
        }

        // the save method automatically generates an Id because of @GeneratedValue on our Id annotation in our Entity
        Message createdMessage = messageRepository.save(message);
        return createdMessage;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    public Message getMessage(int messageId) {
        Message message = messageRepository.findById(messageId).orElse(null);
        return message;
    }

    public int deleteMessages(int messageId) {
        int rows = messageRepository.deleteMessageById(messageId);
        return rows;
    }

    public int updateMessage(int messageId, String messageText) {
        if (messageText.length() > 255 || messageText.equals("{\"messageText\": \"\"}") || messageText.isEmpty()) {
            throw new IllegalArgumentException("Message should not be empty and should not be over 255 characters");
        }

        if (messageRepository.findById(messageId).orElse(null) == null) {
            throw new IllegalArgumentException("Message not found");
        }

        int rows = messageRepository.updateMessageById(messageId, messageText);
        return rows;
    }

    public List<Message> getAllMessagesByUser(int accountId) {
        List<Message> messages = messageRepository.getAllMessagesByUser(accountId);
        return messages;
    }
}
