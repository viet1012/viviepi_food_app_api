//package com.food_app_api.Viviepi.controllers;
//
//import com.food_app_api.Viviepi.entities.Message;
//import com.food_app_api.Viviepi.services.MessageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/messages")
//public class MessageController {
//
//    @Autowired
//    private MessageService messageService;
//
//    @GetMapping
//    public List<Message> getAllMessages() {
//        return messageService.getAllMessages();
//    }
//
//    @GetMapping("/{id}")
//    public Message getMessageById(@PathVariable Long id) {
//        return messageService.getMessageById(id);
//    }
//
//    @PostMapping
//    public Message createMessage(@RequestBody Message message) {
//        return messageService.saveMessage(message);
//    }
//
//    @PutMapping("/{id}")
//    public Message updateMessage(@PathVariable Long id, @RequestBody Message message) {
//        return messageService.updateMessage(id, message);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteMessage(@PathVariable Long id) {
//        messageService.deleteMessage(id);
//    }
//}
