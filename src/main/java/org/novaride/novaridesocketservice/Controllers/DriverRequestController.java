package org.novaride.novaridesocketservice.Controllers;

import org.novaride.novaridesocketservice.Dtos.*;
import org.novaride.novaridesocketservice.producers.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/api/socket")
public class DriverRequestController {
    SimpMessagingTemplate simpMessagingTemplate;
    RestTemplate restTemplate ;
    private final ProducerService producerService;

    public DriverRequestController(SimpMessagingTemplate simpMessagingTemplate,ProducerService producerService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        restTemplate = new RestTemplate();
        this.producerService = producerService;
    }

    @GetMapping
    public Boolean help(){
        producerService.publishMessage("sample-topic", "New Driver has been allocated");
        return true;
    }


    @PostMapping("/newRide")
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

    //this id will be received from client
    @MessageMapping("/rideResponse/{userId}")
    //multiple request should not happen at a time hence used synchronized
    public synchronized void rideResponseHandler(@DestinationVariable String userId, RideResponseDto rideResponseDto) {
        try{
            System.out.println(rideResponseDto.getResponse() +" "+userId);
            UpdateBookingRequestDto requestDto = UpdateBookingRequestDto.builder()
                    .driverId(Optional.of(Long.parseLong(userId)))
                    .status("SCHEDULED")
                    .build();

            System.out.println(requestDto.toString());
            ResponseEntity<UpdateBookingResponseDto> result = this.restTemplate.postForEntity("http://localhost:7475/api/v1/booking/" + rideResponseDto.bookingId, requestDto, UpdateBookingResponseDto.class);
            System.out.println(result.getStatusCode());

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
