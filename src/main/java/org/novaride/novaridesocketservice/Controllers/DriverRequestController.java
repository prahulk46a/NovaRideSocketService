package org.novaride.novaridesocketservice.Controllers;

import org.novaride.novaridesocketservice.Dtos.ChatRequest;
import org.novaride.novaridesocketservice.Dtos.ChatResponse;
import org.novaride.novaridesocketservice.Dtos.RideRequestDto;
import org.novaride.novaridesocketservice.Dtos.RideResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socket")
public class DriverRequestController {
    SimpMessagingTemplate simpMessagingTemplate;
    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @PostMapping("/newride")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody RideRequestDto requestDto) {
        System.out.println("request for rides received");
        sendDriversRideRequest(requestDto);
        System.out.println("Req completed");
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }
    public void sendDriversRideRequest(RideRequestDto rideRequestDto) {
        System.out.println("Executed periodic function");
        //here in payload we can pass ride request that we received from booking service
        simpMessagingTemplate.convertAndSend("/topic/rideRequest", rideRequestDto);
    }

    @MessageMapping("/rideResponse")
    public void rideResponseHandler(RideResponseDto rideResponseDto) {
        System.out.println(rideResponseDto.getResponse());
    }

}
