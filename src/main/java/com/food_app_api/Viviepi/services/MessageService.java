package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.entities.Message;
import com.food_app_api.Viviepi.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

//    public Message saveMessage(Message message) {
//        message.setIsForAdmin(true);
//        return messageRepository.save(message);
//    }
//
//    public Message updateMessage(Long id, Message updatedMessage) {
//        Message message = messageRepository.findById(id).orElse(null);
//        if (message != null) {
//            message.setContent(updatedMessage.getContent());
//            return messageRepository.save(message);
//        }
//        return null;
//    }
//
//    public void deleteMessage(Long id) {
//        messageRepository.deleteById(id);
//    }
}
