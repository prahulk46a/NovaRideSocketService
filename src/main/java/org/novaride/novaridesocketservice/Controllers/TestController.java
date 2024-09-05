package org.novaride.novaridesocketservice.Controllers;

import org.novaride.novaridesocketservice.Dtos.TestRequest;
import org.novaride.novaridesocketservice.Dtos.TestResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {
    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    TestResponse pingCheck(TestRequest testRequest) {
        System.out.println("Received msg from client: " + testRequest.getData());
        return  TestResponse.builder().data("Received").build();
    }
}
