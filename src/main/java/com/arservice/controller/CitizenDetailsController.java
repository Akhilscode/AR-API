package com.arservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.arservice.binding.CitizenInfo;
import com.arservice.service.CitizenService;

@RestController
public class CitizenDetailsController {
	
	@Autowired
	private CitizenService cservice;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerCitizen(@RequestBody CitizenInfo cinfo){
		
		Integer citizenId = cservice.saveCitizen(cinfo);
		if(citizenId != 0) {
			return new ResponseEntity<String>("Citizen Record added successfully with citizenId "+citizenId, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Citizen SSN in not Valid", HttpStatus.BAD_REQUEST);
		}
	}

}
