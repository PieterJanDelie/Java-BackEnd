package be.vives.ti.imageCalender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ImageCalenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageCalenderApplication.class, args);
	}

}
