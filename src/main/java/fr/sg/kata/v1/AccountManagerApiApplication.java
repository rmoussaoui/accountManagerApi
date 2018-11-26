package fr.sg.kata.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AccountManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountManagerApiApplication.class, args);
	}
}
