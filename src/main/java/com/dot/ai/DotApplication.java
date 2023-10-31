package com.dot.ai;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Dot.ai Application",
        description = "Application to simulate transfers between accounts in a bank",
        version = "1.0",
        contact = @Contact(name = "Richard Oyeleke",
                email = "oyelekerichard@gmail.com",
                url = "https://github.com/oyelekerichard/Dot.ai-project.git")))
public class DotApplication {
    public static void main(String[] args) {
        SpringApplication.run(DotApplication.class, args);
    }

}
