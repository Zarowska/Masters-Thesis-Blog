package blog.cirkle.app.ai.generator;

import blog.cirkle.app.ai.generator.api.PersonDescription;
import blog.cirkle.app.model.entity.CustomTimestampListener;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("init-data-generation")
public class InitialDataGenerationService implements ApplicationRunner {

	private final GenerationService generationService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
        //		 generatePersons();
		// fixEmails();
		// registerUsers();
//		 makeFriends();
//		 generatePosts();
	}

	private void makeFriends() {
		List<PersonDescription> persons = generationService.findAllPersons();
		List<PersonDescription> personList = new ArrayList<>(persons);
		persons.forEach(person -> {
			personList.remove(person);
			Collections.shuffle(personList);
			List<PersonDescription> friends = personList.subList(0, (int) (10 + Math.random() * 20));
			friends.forEach(friend -> {
				generationService.makeFriends(person, friend);
			});
			personList.add(person);
		});
	}

	private void generatePosts() {
//		List<PersonDescription> allPersons = generationService.findAllPersonsWithoutPosts();
		List<PersonDescription> allPersons = generationService.findAllPersons();
		CustomTimestampListener.setCurrentTime(Instant.parse("2024-01-01T00:00:00.0Z"));
		ExecutorService executor = Executors.newFixedThreadPool(3);
		allPersons.forEach(person -> {
			executor.submit(() -> {
				generationService.generatePost(person);
				CustomTimestampListener.advance(Duration.ofMillis(1500 + (long) (Math.random() * 5000)));
			});
		});

	}

	private void fixEmails() {
		generationService.fixPersonEmails();
	}

	private void registerUsers() {
		ExecutorService executor = Executors.newFixedThreadPool(3);

		generationService.findAllPersons().forEach(person -> {
			executor.submit(() -> generationService.registerPerson(person));
		});
		executor.shutdown();
		while (!executor.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void generatePersons() {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		while (generationService.getAiPersonsCount() < 1100) {
			for (int i = 0; i < 3; i++) {
				executor.submit(() -> {
					try {
						if (generationService.getAiPersonsCount() < 1100) {
							generateNewPerson();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void generateNewPerson() {
		generationService.generateAiPerson();
	}
}
