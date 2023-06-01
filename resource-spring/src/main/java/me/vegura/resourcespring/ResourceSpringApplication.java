package me.vegura.resourcespring;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ResourceSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceSpringApplication.class, args);
	}

}
