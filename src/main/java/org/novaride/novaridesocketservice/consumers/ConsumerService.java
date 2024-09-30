package org.novaride.novaridesocketservice.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    //Consumer will consume whatever msg that recieved to topic by this topic
    @KafkaListener(topics = "sample-topic")
    public void listen(String message) {

        System.out.println("Kafka message from topic sample topic: " + message);
    }
}
