package blog.cirkle.domain.service.impl;

import blog.cirkle.domain.entity.participant.User;
import blog.cirkle.domain.repository.participant.UserRepository;
import blog.cirkle.domain.security.BlogUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public BlogUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmailAndRoleNot(username, User.UserRole.SYSTEM).map(this::toBlogUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
	}

	private BlogUserDetails toBlogUserDetails(User user) {
		return new BlogUserDetails(user.getId(), user.getEmail(), user.getRole() != User.UserRole.USER,
				user.getPasswordHash());
	}
}
