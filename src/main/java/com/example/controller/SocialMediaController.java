package com.example.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

//     As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. The body will contain a representation of a JSON Account, but will not contain an accountId.
// - The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, and an Account with that username does not already exist. If all these conditions are met, the response body should contain a JSON of the Account, including its accountId. The response status should be 200 OK, which is the default. The new account should be persisted to the database.
// - If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
// - If the registration is not successful for some other reason, the response status should be 400. (Client error)
    AccountService accountService;
    MessageService messageSerivce;
    public SocialMediaController(AccountService accountService, MessageService messageSerivce) {
        this.messageSerivce = messageSerivce;
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account newAccount) {
        Account registeredAccount = accountService.registerAccount(newAccount);
        return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        Account matchedAccount = accountService.loginAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body(matchedAccount);
    }

    // ## 3: Our API should be able to process the creation of new messages.

    // As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages. 
    // The request body will contain a JSON representation of a message, which should be persisted to the database, but will not contain a messageId.

    // The creation of the message will be successful if and only if the messageText is not blank, is not over 255 characters, 
    // and postedBy refers to a real, existing user. If successful, the response body should contain a JSON of the message, 
    // including its messageId. The response status should be 200, which is the default. The new message should be persisted to the database.
    // If the creation of the message is not successful, the response status should be 400. (Client error)

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageSerivce.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageSerivce.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int messageId) {
        Message message = messageSerivce.getMessage(messageId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessages(@PathVariable int messageId) {
        System.out.println("you reached here\n\n");
        Integer rows = messageSerivce.deleteMessages(messageId);
        return ResponseEntity.ok(rows > 0 ? rows : null);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@RequestBody String messageText, @PathVariable int messageId) {
        System.out.println("controller received this message " + messageText);
        int rows = messageSerivce.updateMessage(messageId, messageText);
        return ResponseEntity.ok(rows);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable int accountId) {
        System.out.println("here111111111");
        List<Message> messages = messageSerivce.getAllMessagesByUser(accountId);
        return ResponseEntity.ok(messages);
    }
}
