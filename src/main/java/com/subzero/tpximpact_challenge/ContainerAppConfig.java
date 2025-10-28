package com.subzero.tpximpact_challenge;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Configuration
@PropertySource("classpath:containerised-db.properties")
@ConfigurationProperties(prefix = "db")
public class ContainerAppConfig {

	@Getter @Setter
    private String url;

	@Getter @Setter
    private String username;

	@Getter @Setter
    private String password;

	@Getter @Setter
    private String name;
	
	@Getter @Setter
    private String driver;

	@PostConstruct
    public void init() {
        System.out.println("AppConfig initialized with URL: " + url + ", USER: " + username + ", DB: " + name);
    }
}