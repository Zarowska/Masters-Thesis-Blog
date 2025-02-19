package blog.cirkle.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class CirkleApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CirkleApplication.class);
		// app.setAdditionalProfiles("data-import");
		app.run(args);
	}

}
