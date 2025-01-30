package blog.cirkle.app.ai.generator;

import blog.cirkle.app.ai.generator.api.PersonDescription;
import blog.cirkle.app.api.rest.model.ParticipantDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.facade.AuthFacade;
import blog.cirkle.app.facade.PostsFacade;
import blog.cirkle.app.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimulationTrigger implements ApplicationRunner {

    private final GenerationService generationService;
    private final UserFacade userFacade;
    private final AuthFacade authFacade;
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final PostsFacade postsFacade;
    private final Random random = new Random();


    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<PersonDescription> persons = generationService.findAllPersons();
        for (int i = 0; i < 5000; i++) {
            executorService.submit(()->simulate(persons.get(random.nextInt(persons.size()))));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(1000);
        }
    }

    private void simulate(PersonDescription personDescription) {

        authFacade.authenticateByUsernameAndPassword(personDescription.getEmail(), personDescription.getEmail());

        log.info("Commenting own posts for user {}", personDescription.getEmail());
        ParticipantDto currentUserInfo = userFacade.getCurrentUserInfo();
        userFacade.listCurrentUserPosts(Pageable.ofSize(5)).forEach(p -> generationService.commentPost(currentUserInfo.getId(), personDescription, p));

        log.info("Commenting new posts for user {}", personDescription.getEmail());
        Page<PostDto> newPosts = userFacade.listCurrentUserFeed(Pageable.ofSize(10));
        for (PostDto post : newPosts) {
            generationService.commentPost(currentUserInfo.getId(), personDescription, post);
        }
        log.info("Creating post for user {}", personDescription.getEmail());
        generationService.generatePost(personDescription);
    }
}
