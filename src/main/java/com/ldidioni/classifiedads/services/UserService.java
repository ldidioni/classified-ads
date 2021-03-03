package com.ldidioni.classifiedads.services;

import java.util.Arrays;
import java.util.HashSet;

import com.ldidioni.classifiedads.models.Tag;
import com.ldidioni.classifiedads.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ldidioni.classifiedads.models.Role;
import com.ldidioni.classifiedads.models.User;
import com.ldidioni.classifiedads.repositories.RoleRepository;
import com.ldidioni.classifiedads.repositories.UserRepository;
import com.ldidioni.classifiedads.repositories.AdRepository;


@Service("userService")
public class UserService
{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdRepository adRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User findUserByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}

	public void saveUser(User user)
	{
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	public String getCurrentUsername() {

		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public boolean isAdmin() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
	}

	public void update(int id, User user) {
		userRepository.findById(id).ifPresent(existingUser -> {
			existingUser.setUsername(user.getUsername());
			existingUser.setEmail(user.getEmail());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			/*
			if(user.getPassword() != null && user.getPassword().length() > 5)
				existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));*/

			userRepository.save(existingUser);
		});
	};
/*
	public boolean isSeller(int adId) {

		String username = adRepository.getOne(adId).getSeller().getUsername();
		return getCurrentUsername() == username;
	}*/
}