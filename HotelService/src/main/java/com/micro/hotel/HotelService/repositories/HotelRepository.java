package com.micro.hotel.HotelService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.micro.hotel.HotelService.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,String> {

}
