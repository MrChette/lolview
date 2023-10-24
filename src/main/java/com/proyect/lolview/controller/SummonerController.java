package com.proyect.lolview.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyect.lolview.Config;
import com.proyect.lolview.model.SummonerModel;
import com.proyect.lolview.serviceImpl.SummonerServiceImpl;

@RestController
@RequestMapping("/api")
public class SummonerController {
	
	private final Config config = new Config();
	
	public String _baseUrl = config.get_baseUrl();
	public String _apiKey = config.get_apiKey();
	
	@Autowired
	@Qualifier("summonerServiceImpl")
	private SummonerServiceImpl userService;

	
	@Autowired
	private SummonerLeagueController summonerLeagueController;
	
	
	@GetMapping(path = "/lol/addsummoner/{username}")
	public ResponseEntity<SummonerModel> getSummonerInfo(@PathVariable String username) {
	    RestTemplate restTemplate = new RestTemplate();

	    String url = _baseUrl + "/lol/summoner/v4/summoners/by-name/" + username + "?api_key=" + _apiKey;
	    System.out.println(url);
	    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

	    if (response.getStatusCode().is2xxSuccessful()) {
	        ObjectMapper mapper = new ObjectMapper();
	        try {
	        	SummonerModel user = mapper.readValue(response.getBody(), SummonerModel.class);

	            // Verificar si el usuario ya existe en la base de datos
	            if (userService.findEntityById(user.getId()) == null) {
	                userService.addEntity(user);
	                
	                System.out.println(user.getId());
	                
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
	
	
	
	

}
