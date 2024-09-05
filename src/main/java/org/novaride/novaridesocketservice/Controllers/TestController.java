package org.novaride.novaridesocketservice.Controllers;

import org.novaride.novaridesocketservice.Dtos.ChatRequest;
import org.novaride.novaridesocketservice.Dtos.ChatResponse;
import org.novaride.novaridesocketservice.Dtos.TestRequest;
import org.novaride.novaridesocketservice.Dtos.TestResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {
    private final SimpMessagingTemplate simpMessagingTemplate;


    public TestController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    TestResponse pingCheck(TestRequest testRequest) {
        System.out.println("Received msg from client: " + testRequest.getData());
        return  TestResponse.builder().data("Received").build();
    }

    @Scheduled(fixedDelay = 2000)
    public void sendPeriodicMessage() {
        System.out.println("Executed periodic function");
        simpMessagingTemplate.convertAndSend("/topic/scheduled", "Periodic Message sent " + System.currentTimeMillis());
    }

    @MessageMapping("/chat/{room}")
    @SendTo("/topic/message/{room}")
    public ChatResponse chatMessage(@DestinationVariable String room, ChatRequest request) {
        return ChatResponse.builder()
                .name(request.getName())
                .message(request.getMessage())
                .timeStamp("" + System.currentTimeMillis())
                .build();
    }

    @MessageMapping("/privateChat/{room}/{userId}")
    @SendTo("/topic/privateMessage/{room}/{userId}")
    public void privateChatMessage(@DestinationVariable String room, @DestinationVariable String userId, ChatRequest request) {
        ChatResponse response =  ChatResponse.builder()
                .name(request.getName())
                .message(request.getMessage())
                .timeStamp("" + System.currentTimeMillis())
                .build();
        simpMessagingTemplate.convertAndSendToUser(userId, "/queue/privateMessage/" + room, response);
    }


}
