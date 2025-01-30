package blog.cirkle.app.ai.generator;

import blog.cirkle.app.ai.generator.api.ImageGenerationRequest;
import blog.cirkle.app.ai.generator.api.PersonDescription;
import blog.cirkle.app.ai.generator.api.PostDescription;
import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.*;
import blog.cirkle.app.facade.AuthFacade;
import blog.cirkle.app.facade.ImageFacade;
import blog.cirkle.app.facade.PostsFacade;
import blog.cirkle.app.facade.UserFacade;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.model.entity.ai.PersonDescriptionEntity;
import blog.cirkle.app.repository.PersonDescriptionEntityRepository;
import blog.cirkle.app.repository.PersonInterestEntityRepository;
import blog.cirkle.app.repository.UserRepository;
import blog.cirkle.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static blog.cirkle.app.ai.generator.GenerationUtils.randomDecision;
import static blog.cirkle.app.ai.generator.GenerationUtils.randomEmail;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@Profile("data-generation")
public class GenerationService {

    private final PersonDescriptionEntityRepository personDescriptionEntityRepository;
    private final UserFacade userFacade;
    private final ImageFacade imageFacade;
    private final UserService userService;
    private final PersonGenerator personGenerator;
    private final ImageGenerator imageGenerator;
    private final PersonInterestEntityRepository personInterestEntityRepository;
    private final UserRepository userRepository;
    private final PostsFacade postsFacade;
    private final PostGenerator postGenerator;
    private final AuthFacade authFacade;
    private final CommentGenerator commentGenerator;
    private final Random random = new Random();


    @Transactional
    public PersonDescription generateAiPerson() {
        PersonDescription description = personGenerator.generatePerson();
        return personDescriptionEntityRepository.save(new PersonDescriptionEntity(description)).toDto();
    }

    @Transactional(readOnly = true)
    public long getAiPersonsCount() {
        return personDescriptionEntityRepository.count();
    }

    @Transactional(readOnly = true)
    public List<PersonDescription> findAllPersons() {
        return personDescriptionEntityRepository.findAll().stream().map(PersonDescriptionEntity::toDto).toList();
    }

    public void registerPerson(PersonDescription person) {
        try {
            if (userService.findByEmail(person.getEmail()).isEmpty()) {
                NewUserDto registrationDTO = userFacade.registerUser(
                        CreateUserDto.builder().fullName(person.getFullName()).email(person.getEmail()).build());
                userFacade.resetPassword(registrationDTO.getPasswordResetId(), new ResetPasswordDto(person.getEmail()));
                File avatar = imageGenerator.generateAvatar(person);
                ImageDto imageDto = imageFacade.uploadImage(avatar);
                userFacade.updateProfile(UpdateUserProfileDto.builder().profileImageId(imageDto.getId())

                        .city(person.getLiveCity()).country(person.getLiveCountry()).bio(person.getBio()).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void fixPersonEmails() {
        List<PersonDescriptionEntity> all = personDescriptionEntityRepository.findAll();
        all.forEach(person -> {
            String email = person.getEmail();
            if (email.contains("@example.com")) {
                person.setEmail(randomEmail(email));
            }
        });

        Map<String, List<PersonDescriptionEntity>> duplicates = all.stream()
                .collect(Collectors.groupingBy(PersonDescriptionEntity::getEmail)).entrySet().stream()
                .filter(it -> it.getValue().size() > 1).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        duplicates.forEach((email, persons) -> {
            User existent = userRepository.findByEmailIgnoreCase(persons.get(0).getEmail()).get();
            String city = existent.getProfile().getAddress().getCity();
            String country = existent.getProfile().getAddress().getCountry();
            List<PersonDescriptionEntity> ed = new ArrayList<>(persons);
            ed.removeIf(it -> it.getLiveCity().equals(city) && it.getLiveCountry().equals(country));
            ed.forEach(p -> {
                String newEmail = randomEmail(email);
                while (newEmail.equals(email)) {
                    newEmail = randomEmail(email);
                }
                p.setEmail(newEmail);
            });
        });

    }

    @Transactional
    public void generatePost(PersonDescription person) {
        authFacade.authenticateByUsernameAndPassword(person.getEmail(), person.getEmail());
        PostDescription postDescription = postGenerator.generatePost(person);
        List<ImageDto> postImages = postDescription.getImages().stream().map(i -> {
            File imgFile = imageGenerator.generateImage(ImageGenerationRequest.builder().author(person)
                    .photoType(i.getType()).setting(i.getSetting()).activity(i.getActivity()).build());
            return imageFacade.uploadImage(imgFile);
        }).toList();
        PostDto post = postsFacade.createPost(CreatePostDto.builder().text(postDescription.getPost())
                .images(postImages.stream().map(ImageDto::getId).toList()).build());
    }

    public void makeFriends(PersonDescription person, PersonDescription friend) {
        User to = userRepository.findByEmailIgnoreCase(friend.getEmail()).get();
        authFacade.authenticateByUsernameAndPassword(person.getEmail(), person.getEmail());
        userFacade.friendUserByUserId(to.getId());
        authFacade.authenticateByUsernameAndPassword(friend.getEmail(), friend.getEmail());
        List<RequestDto> content = userFacade.listUserRequests(to.getId(), PageRequest.ofSize(1000)).getContent();
        content.forEach(request -> userFacade.acceptRequest(request.getId()));
    }

    @Transactional(readOnly = true)
    public List<PersonDescription> findAllPersonsWithoutPosts() {
        return personDescriptionEntityRepository.findPersonsWithoutPosts().stream().map(PersonDescriptionEntity::toDto)
                .toList();
    }

    public void commentPost(UUID userId, PersonDescription personDescription, PostDto postDto) {
        if (!postDto.getAuthor().getId().equals(userId)) {
            if (randomDecision(50)) {
                postsFacade.createReactionByPostId(postDto.getId(), CreateReactionDto.of(1 + random.nextInt(5)));
            }

            if (randomDecision(30)) {
                String commnent = commentGenerator.commentPost(personDescription, postDto);
                postsFacade.createComment(postDto.getId(), null, CreateCommentDto.builder().text(commnent).build());
            }
        }
        postsFacade.listRootCommentsByPostId(postDto.getId(), Pageable.ofSize(1000)).getContent().stream()
                .forEach(it -> commentThread(userId, personDescription, postDto, List.of(it)));
    }

    private void commentThread(UUID userId, PersonDescription personDescription, PostDto postDto, List<CommentDto> thread) {
        CommentDto last = thread.getLast();
        List<CommentDto> childs = postsFacade.listCommentsByPostIdAndCommentId(postDto.getId(), last.getId(), Pageable.ofSize(1000)).getContent();
        if (childs.isEmpty() && !last.getAuthor().getId().equals(userId)) {
            if (randomDecision(50)) {
                if (randomDecision(50)) {
                    postsFacade.createReactionByPostIdAndCommentId(postDto.getId(), last.getId(), CreateReactionDto.of(1 + random.nextInt(5)));
                }
                String comment = commentGenerator.commentThread(personDescription, postDto, thread);
                postsFacade.createComment(postDto.getId(), last.getId(), CreateCommentDto.builder().text(comment).build());
            } else {
                childs.forEach(c -> {
                    ArrayList<CommentDto> newThread = new ArrayList<>(thread);
                    newThread.add(c);
                    commentThread(userId, personDescription, postDto, newThread);
                });
            }
        }
    }

}
