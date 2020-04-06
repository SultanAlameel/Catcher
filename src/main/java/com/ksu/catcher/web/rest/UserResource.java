package com.ksu.catcher.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksu.catcher.domain.User;
import com.ksu.catcher.repository.UserRepository;
import com.ksu.catcher.web.rest.vo.UserVO;

@RestController
@RequestMapping("/users")
public class UserResource {
private final UserRepository userRepository ;
	
	public UserResource(UserRepository userRepository) {
		this.userRepository = userRepository ;
}
	
	
@PostMapping
public Long createUser(@RequestBody UserVO userVO) {
	
	
	User user = new User();
	
	user.setFirstName(userVO.getFirstName());
	user.setLastName(userVO.getLastName());
	user.setEmail(userVO.getEmail());
    user.setPassword(userVO.getPassword());
    
    user = userRepository.save(user);
    return user.getId();
}

@GetMapping("/{id}")
public User getUsers(@PathVariable Long id) {
	return userRepository.findById(id).get();
	
}
	
	
	
}
