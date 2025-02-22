package com.micro.hotel.HotelService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.hotel.HotelService.entities.Hotel;
import com.micro.hotel.HotelService.services.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
	}

	@PreAuthorize("hasAuthority('SCOPE_internal')")
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> get(@PathVariable String hotelId){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
	}

	@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('ADMIN')")
	@GetMapping("/getAll")
	public ResponseEntity<List<Hotel>> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
	}
}
