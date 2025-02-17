package com.micro.userService.services;

import java.util.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.userService.External.Services.HotelService;
import com.micro.userService.entities.Hotel;
import com.micro.userService.entities.Rating;
import com.micro.userService.entities.User;
import com.micro.userService.exception.ResourceNotFoundException;
import com.micro.userService.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;	
	
	@Autowired
	private HotelService hotelService;
	
	@Override
	public User saveUser(User user) {
		// Generate Unique UserID 
		String randomUserId= UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		List<User> userList = userRepository.findAll();
//		for(int i=0; i<userList.size(); i++) {
//			User user = userList.get(i);
//			ArrayList<Rating> ratingList = restTemplate.getForObject("http://localhost:8082/ratings/users/"+user.getUserId(), ArrayList.class);
//			user.setRatings(ratingList);
//		}
		
		List<User> usersWithRating = userList.stream().map(user -> {
			Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
			
			List<Rating> ratingList = Arrays.stream(ratings).toList();
			user.setRatings(ratingList);
			
			List<Rating> ratingOfHotel =  ratingList.stream().map(rating->{
				Hotel hotel = hotelService.getHotel(rating.getHotelId()); // Using Feign Client
				rating.setHotel(hotel);
				return rating;
			}).collect(Collectors.toList());
			
			return user;
		}).collect(Collectors.toList());
		return usersWithRating;
	}

	@Override
	public User getUser(String UserId) {
		User user = userRepository.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User With Given Id Not Found On Server!!"+ UserId));
		
		Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		
		List<Rating> ratingList = Arrays.stream(ratings).toList();
		
		List<Rating> ratingOfHotel =  ratingList.stream().map(rating->{
//			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//			Hotel hotel = forEntity.getBody();
			Hotel hotel = hotelService.getHotel(rating.getHotelId()); // Using Feign Client
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingOfHotel);
		return user;
	}

}
