package org.novaride.novaridesocketservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("org.novaride.modelentity.models")
@EnableKafka
public class NovaRideSocketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovaRideSocketServiceApplication.class, args);
    }

}
