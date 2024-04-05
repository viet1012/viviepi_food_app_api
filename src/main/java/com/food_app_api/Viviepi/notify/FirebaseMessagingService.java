package com.food_app_api.Viviepi.notify;

import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FirebaseMessagingService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendNotificationByToken(NotificationMessage notificationMessage) {
        Notification notification = Notification.builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage(notificationMessage.getImage())
                .build();

        MulticastMessage.Builder messageBuilder = MulticastMessage.builder()
                .setNotification(notification)
                .putAllData(notificationMessage.getData());

        // Convert the list of tokens to an array of strings
        List<String> tokens = notificationMessage.getRegistrationTokens();
        String[] tokensArray = tokens.toArray(new String[0]);

        // Build the multicast message
        MulticastMessage message = messageBuilder.addAllTokens(Arrays.asList(tokensArray)).build();

        try {
            BatchResponse response = firebaseMessaging.sendMulticast(message);
            // Check if there were any errors in sending notifications to individual devices
            if (response.getFailureCount() == 0) {
                return "Success Sending Notification";
            } else {
                // Handle failure cases
                List<SendResponse> responses = response.getResponses();
                StringBuilder errorMessage = new StringBuilder();
                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        String error = "Error sending notification to " + tokens.get(i) + ": " + responses.get(i).getException().getMessage();
                        errorMessage.append(error).append("\n");
                    }
                }
                return errorMessage.toString();
            }
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error Sending Notification";
        }
    }
}
