package com.example.redis_practice.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class ChatService implements MessageListener {

    private final RedisMessageListenerContainer container;
    private final RedisTemplate<String, String> redisTemplate;

    public void enterChatRoom(String chatRoomName) {
        // Pub(Redis) -> Sub(App)
        container.addMessageListener(this, new ChannelTopic(chatRoomName));

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals("q")) {
                System.out.println("Quit");
                break;
            }

            // Sub(App) -> Pub(Redis)
            redisTemplate.convertAndSend(chatRoomName, line);
        }
        container.removeMessageListener(this);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("message: " + message.toString());
    }
}
