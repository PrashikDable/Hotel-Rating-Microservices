package com.micro.rating.RatingService.services;

import java.util.List;

import com.micro.rating.RatingService.entities.Rating;

public interface RatingService {

	Rating createRating(Rating rating);
	
	List<Rating> getAll();
	
	List<Rating> getRatingByUserID(String userId);
	
	List<Rating> getRatingByHoteID(String hotelId);
}
