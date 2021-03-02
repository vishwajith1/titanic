package com.egen.titanic;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
@EnableSwagger2
@Log4j2
public class TitanicApplication {

	public static void main(String[] args) {
		SpringApplication.run(TitanicApplication.class, args);
		log.info("Info Level log Message is Enabled");
		log.debug("Debug Level log Message is Enabled");
		log.error("Error Level log Message is Enabled");
	}

}
