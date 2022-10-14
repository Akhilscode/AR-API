package com.arservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.arservice.binding.CitizenInfo;
import com.arservice.entity.CitizenDetailsEntity;
import com.arservice.repository.CitizenDetailsRepository;

@Service
public class CitizenServiceImpl implements CitizenService {
	
	@Autowired
	private CitizenDetailsRepository crepo;

	@Override
	public Integer saveCitizen(CitizenInfo cinfo) {
		
		//making rest call in order to get citizen belongs to which state
		String endpointurl  = "https://ssa-web-api.herokuapp.com/ssn/{ssn}";
		
	     //creating RestTemplate in order to make the rest call
		/*RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.getForEntity(endpointurl, String.class, cinfo.getSsn());
		String stateName = response.getBody();*/
		
		//making rest call using web client
		
		 WebClient webClient = WebClient.create();
		 String stateName  =     webClient.get()
		                                     .uri(endpointurl, cinfo.getSsn())
		                                     .retrieve()
		                                     .bodyToMono(String.class)
		                                     .block();
		//if citizen belongs to New Jersey then save the record otherwise don't save the record
		//in endpoint url ssn number start with 6 series belongs to New Jersey
		if(stateName.equalsIgnoreCase("New Jersey")) {
			CitizenDetailsEntity centity = new CitizenDetailsEntity();
			
			BeanUtils.copyProperties(cinfo, centity);
			
			centity.setStateName(stateName);
			
			CitizenDetailsEntity save = crepo.save(centity);
			
			return save.getCitizenId();
		}
		
		
		return 0;
	}

}
