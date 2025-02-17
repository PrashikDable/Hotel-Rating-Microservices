package com.micro.userService.services;

import java.util.List;
import com.micro.userService.entities.User;

public interface UserService {

	User saveUser(User user);
//	
	List<User> getAllUser();
	
	User getUser(String UserId);
	
	// TODO: Update
	// TODO: Delete
}
