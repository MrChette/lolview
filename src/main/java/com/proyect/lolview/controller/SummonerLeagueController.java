package com.proyect.lolview.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.proyect.lolview.model.SummonerLeagueModel;
import com.proyect.lolview.serviceImpl.SummonerLeagueServiceImpl;

import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/api")
public class SummonerLeagueController {

	private final Config config = new Config();

	public String _baseUrl = config.get_baseUrl();
	public String _apiKey = config.get_apiKey();

	@Autowired
	@Qualifier("summonerLeagueServiceImpl")
	private SummonerLeagueServiceImpl summonerLeagueService;

	@GetMapping(path = "/lol/getsummonerleague/{summonnerid}")
	public ResponseEntity<List<SummonerLeagueModel>> getSummonerLeague(@PathVariable String summonnerid) {
		RestTemplate restTemplate = new RestTemplate();

		String url = _baseUrl + "/lol/league/v4/entries/by-summoner/" + summonnerid + "?api_key=" + _apiKey;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				List<SummonerLeagueModel> userLeagues = mapper.readValue(response.getBody(),
						new TypeReference<List<SummonerLeagueModel>>() {
						});

				List<SummonerLeagueModel> result = new ArrayList<>();

				for (SummonerLeagueModel userLeague : userLeagues) {
					if (summonerLeagueService.findEntityById(userLeague.getLeagueId()) == null) {
						summonerLeagueService.addEntity(userLeague);
						result.add(userLeague);
					}
				}

				return ResponseEntity.ok(result);
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return ResponseEntity.status(response.getStatusCode()).build();
		}
	}

}
