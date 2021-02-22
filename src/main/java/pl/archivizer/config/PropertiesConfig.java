package pl.archivizer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cron")
@Getter
@Setter
public class PropertiesConfig {
    private String expresion;

    @Bean
    public String expresion() {
        return this.expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }
}