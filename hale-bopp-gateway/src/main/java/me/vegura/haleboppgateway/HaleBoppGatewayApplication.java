package me.vegura.haleboppgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HaleBoppGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaleBoppGatewayApplication.class, args);
	}

}
