package com.micro.rating.RatingService.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.rating.RatingService.entities.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String>{

	List<Rating> findByUserId(String userID);

	List<Rating> findByHotelId(String hotelId);

	
}
