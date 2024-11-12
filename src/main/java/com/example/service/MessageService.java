package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
            throw new IllegalArgumentException("Message should not be empty nor should it be longer than 255 characters.");
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
}
