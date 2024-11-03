package blog.cirkle.app.service.impl;

import static blog.cirkle.app.model.entity.SystemConfigEntry.*;

import blog.cirkle.app.model.entity.Image;
import blog.cirkle.app.model.entity.User;
import blog.cirkle.app.repository.UserRepository;
import blog.cirkle.app.service.ImageService;
import blog.cirkle.app.service.InitialDataLoaderService;
import blog.cirkle.app.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Profile("!test")
public class InitialDataLoaderServiceImpl implements InitialDataLoaderService, ApplicationRunner {

	private final SystemConfigService systemConfigService;
	private final UserRepository userRepository;
	private final ImageService imageService;

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		if (userRepository.count() == 0) {
			User adminUser = new User("admin@cirkle.blog");
			adminUser.getProfile().setName("admin");
			adminUser.getProfile().setHandle("admin");
			userRepository.save(adminUser);
			Image profileImage = imageService.uploadImage(adminUser,
					new ClassPathResource("default-data/profile-photo.svg"));
			Image coverPhoto = imageService.uploadImage(adminUser,
					new ClassPathResource("default-data/cover-photo.svg"));
			systemConfigService.setValue(ADMIN_USER_ID, adminUser.getId().toString());
			systemConfigService.setValue(DEFAULT_IMAGE_PROFILE, profileImage.getId().toString());
			systemConfigService.setValue(DEFAULT_IMAGE_COVER, coverPhoto.getId().toString());
		}

	}
}
