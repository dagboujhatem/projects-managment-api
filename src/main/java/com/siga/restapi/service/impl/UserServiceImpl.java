package com.siga.restapi.service.impl;

import com.siga.restapi.entities.ERole;
import com.siga.restapi.entities.User;
import com.siga.restapi.exceptions.EmailAlreadyUsedException;
import com.siga.restapi.exceptions.ResourceNotFoundException;
import com.siga.restapi.payload.requests.CreateUserRequest;
import com.siga.restapi.payload.requests.UpdateUserRequest;
import com.siga.restapi.repositories.UserRepository;
import com.siga.restapi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService , UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null){
			throw new UsernameNotFoundException("Utilisateur non trouvé.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user.getRole()));
	}

	private List<SimpleGrantedAuthority> getAuthority(ERole role) {
		return Arrays.asList(new SimpleGrantedAuthority(role.toString()));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public void delete(int id) {
		Optional<User> userData = this.userRepository.findById(id);
		if (userData.isPresent()) {
			this.userRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("Utilisateur non trouvé.");
		}
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findById(int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé."));
	}

    public User update(int id, UpdateUserRequest updateUserRequest) throws EmailAlreadyUsedException {
        User user = findById(id);
        if(user != null) {
			// test if email already used
        	if (userRepository.existsByEmailAndIdNot(updateUserRequest.getEmail(), id)){
				throw new EmailAlreadyUsedException("Cette adresse e-mail est déjà utilisée.");
			}
            BeanUtils.copyProperties(updateUserRequest, user, "password");
			if(!updateUserRequest.getPassword().isEmpty()){
				user.setPassword(bcryptEncoder.encode(updateUserRequest.getPassword()));
			}
            userRepository.save(user);
        }
        return null;
    }

    public User save(CreateUserRequest createUserRequest) throws EmailAlreadyUsedException {
		// test if email already used
		if (this.userRepository.existsByEmail(createUserRequest.getEmail())) {
			throw new EmailAlreadyUsedException("Cette adresse e-mail est déjà utilisée.");
		}
		// Create new user account
	    User newUser = new User();
	    newUser.setFirstName(createUserRequest.getFirstName());
		newUser.setLastName(createUserRequest.getLastName());
		newUser.setEmail(createUserRequest.getEmail());
		newUser.setPassword(bcryptEncoder.encode(createUserRequest.getPassword()));
		newUser.setRole(createUserRequest.getRole());
        User createdUser =  userRepository.save(newUser);
        return createdUser;
    }

	// Profile section
	public User getAuthProfile(Authentication authentication){
		if(!userRepository.existsByEmail(authentication.getName())){
			throw new ResourceNotFoundException("Utilisateur non trouvé.");
		}
		System.out.println(authentication.getAuthorities());
		return userRepository.findByEmail(authentication.getName());
	}

	public void updateAuthProfile(Principal principal, UpdateUserRequest updateUserRequest) throws EmailAlreadyUsedException {
		User user = userRepository.findByEmail(principal.getName());
		user.setFirstName(updateUserRequest.getFirstName());
		user.setLastName(updateUserRequest.getLastName());
		if(!updateUserRequest.getPassword().isEmpty()){
			user.setPassword(bcryptEncoder.encode(updateUserRequest.getPassword()));
		}
		userRepository.save(user);
	}
}
