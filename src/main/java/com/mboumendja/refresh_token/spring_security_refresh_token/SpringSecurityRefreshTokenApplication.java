package com.mboumendja.refresh_token.spring_security_refresh_token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mboumendja.refresh_token.spring_security_refresh_token.config.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SpringSecurityRefreshTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityRefreshTokenApplication.class, args);
	}

}
