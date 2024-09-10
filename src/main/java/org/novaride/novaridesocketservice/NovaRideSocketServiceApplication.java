package org.novaride.novaridesocketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("org.novaride.modelentity.models")
public class NovaRideSocketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovaRideSocketServiceApplication.class, args);
    }

}
