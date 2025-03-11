package in.sonalp.exceljsondemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@SpringBootApplication
public class ExcelJsonDemoApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(ExcelJsonDemoApplication.class, args);
        Desktop.getDesktop().browse(URI.create("http://localhost:8080"));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
