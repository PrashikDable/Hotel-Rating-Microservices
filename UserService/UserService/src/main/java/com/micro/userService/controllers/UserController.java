package com.micro.userService.controllers;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.micro.userService.entities.User;
import com.micro.userService.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
	
	 //single user get
    @GetMapping("/{userId}")
    @CircuitBreaker(name= "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelLimiter")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // Fallback Method
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception e){
        logger.info("Fallback is executed because service is down.",e.getMessage());

        User user = User.builder()
                .email("dummyFallback@gmail.com")
                .name("dummyFallBack")
                .about("User is created because this service is down")
                .userId("234234728734")
                .build();

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
// Rate Limiter
    public ResponseEntity<User> ratingHotelLimiter(String userId, Exception e){
        logger.info("Fallback is executed because of multiple request.",e.getMessage());

        User user = User.builder()
                .email("dummyRateLimiter@gmail.com")
                .name("dummyRateLimiter")
                .about("User is created because of multiple request to the API.")
                .userId("234234728734")
                .build();

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    //all user get
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }


}
