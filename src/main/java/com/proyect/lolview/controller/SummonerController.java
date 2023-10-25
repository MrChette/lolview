package com.proyect.lolview.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyect.lolview.Config;
import com.proyect.lolview.model.SummonerModel;
import com.proyect.lolview.serviceImpl.SummonerServiceImpl;



@RestController
@RequestMapping("/api")
public class SummonerController {
	
	private final Config config = new Config();
	
	public String _euwBaseUrl = config.get_euwBaseUrl();
	public String _europeBaseUrl = config.get_europeBaseUrl();
	public String _apiKey = config.get_apiKey();
	
	@Autowired
	@Qualifier("summonerServiceImpl")
	private SummonerServiceImpl userService;

	
	@Autowired
	private SummonerLeagueController summonerLeagueController;
	
	
	@GetMapping(path = "/lol/addsummoner/{username}")
	public ResponseEntity<?> getSummonerInfo(@PathVariable String username) {
	    RestTemplate restTemplate = new RestTemplate();

	    
	    String url = UriComponentsBuilder.fromHttpUrl(_euwBaseUrl)
	            .path("/lol/summoner/v4/summoners/by-name/{username}")
	            .queryParam("api_key", _apiKey)
	            .buildAndExpand(username)
	            .toUriString();
	    
	    System.out.println(url);
	    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

	    if (response.getStatusCode().is2xxSuccessful()) {
	        ObjectMapper mapper = new ObjectMapper();
	        try {
	        	SummonerModel user = mapper.readValue(response.getBody(), SummonerModel.class);

	            // Verificar si el usuario ya existe en la base de datos
	            if (userService.findEntityById(user.getId()) == null) {
	                userService.addEntity(user);
	                
	                //ResponseEntity<List<SummonerLeagueModel>> summonerLeagueModel = 
	                summonerLeagueController.getSummonerLeague(user.getId());
	                
	                return ResponseEntity.ok(user);
	            } else {
	                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Usuario ya existe
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    } else {
	        return ResponseEntity.status(response.getStatusCode()).build();
	    }
	}
	
	@GetMapping(path = "/lol/getsummonermatches/")
	public ResponseEntity<List<String>> getSummonerMatches(
			@RequestParam String summonerPuuid,
	        @RequestParam(defaultValue = "5") int count
	        ) {

	    RestTemplate restTemplate = new RestTemplate();

	    String url = UriComponentsBuilder.fromHttpUrl(_europeBaseUrl)
	            .path("/lol/match/v5/matches/by-puuid/{summonerPuuid}/ids")
	            .queryParam("start", 0)
	            .queryParam("count", count)
	            .queryParam("api_key", _apiKey)
	            .buildAndExpand(summonerPuuid)
	            .toUriString();
	    
	    System.out.println(url);
	   
	    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
	    ObjectMapper objectMapper = new ObjectMapper();

	    if (response.getStatusCode().is2xxSuccessful()) {
	    	String responseBody = response.getBody();
	        try {
	        	List<String> summonerMatches = objectMapper.readValue(responseBody, new TypeReference<List<String>>() {});

	        	return ResponseEntity.ok(summonerMatches); 
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    } else {
	        return ResponseEntity.status(response.getStatusCode()).build();
	    }
	}
	
	
	
	

}
