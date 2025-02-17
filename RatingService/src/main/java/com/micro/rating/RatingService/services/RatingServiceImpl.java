package com.micro.rating.RatingService.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.rating.RatingService.entities.Rating;
import com.micro.rating.RatingService.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService{

	@Autowired
	private RatingRepository ratingRepository;
	@Override
	public Rating createRating(Rating rating) {
		String id = UUID.randomUUID().toString();
		rating.setRatingID(id);
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getAll() {
		return ratingRepository.findAll();
	}

	@Override
	public List<Rating> getRatingByUserID(String userID) {
		
		return ratingRepository.findByUserId(userID);
	}

	@Override
	public List<Rating> getRatingByHoteID(String hotelId) {
		
		return ratingRepository.findByHotelId(hotelId);
	}
	
	
}
