package com.proyect.lolview.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyect.lolview.Config;
import com.proyect.lolview.entity.Top10Summoner;
import com.proyect.lolview.model.Top10SummonerModel;
import com.proyect.lolview.serviceImpl.Top10SummonerServiceImpl;



@RestController
@RequestMapping("/api")
public class Top10SummonerController {
	
	private final Config config = new Config();
	public String _euwBaseUrl = config.get_euwBaseUrl();
	public String _europeBaseUrl = config.get_europeBaseUrl();
	public String _apiKey = config.get_apiKey();
	

	@Autowired
	@Qualifier("top10SummonerServiceImpl")
	private Top10SummonerServiceImpl top10SummonerService;

	
	@GetMapping(path= "/lol/updatetopten")
	public ResponseEntity<List<Top10Summoner>> getTopTen(){
		

	    List<String> regions = Arrays.asList("BR1","EUN1","EUW1","JP1","KR","LA1","LA2","NA1","OC1","PH2","RU","SG2","TH2","TR1","VN2");
	    List<Top10Summoner> list = new ArrayList<Top10Summoner>();

	    ExecutorService executor = Executors.newFixedThreadPool(regions.size());
	    RestTemplate restTemplate = new RestTemplate();
	    

	    try {
	        List<Future<?>> futures = new ArrayList<>();

	        for(String region : regions) {
	        	
	            futures.add(executor.submit(() -> {
	            	String url = buildChallengerLeagueUrl(region.toString());

	                long startTime = System.currentTimeMillis();
	                
	                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
	                
	                long endTime = System.currentTimeMillis(); // Obtener el tiempo después de la solicitud
	                
	                long duration = endTime - startTime;
	                
	                System.out.println("Tiempo de respuesta para " + region + ": " + duration + " ms");

	                ObjectMapper objectMapper = new ObjectMapper();

	                try {
	                    JsonNode root = objectMapper.readTree(response.getBody());
	                    JsonNode entries = root.get("entries");

	                    list.addAll(parseTop10Summoners(entries, region.toString()));

	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }));
	        }

	        for (Future<?> future : futures) {
	            future.get(); // Espera a que todos los hilos terminen
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        executor.shutdown(); // Cuanto termine el proceso matar el executor
	    }

	    list.sort(Comparator.comparingInt(Top10Summoner::getLeaguePoints).reversed());

	    // Obtén los primeros 10 elementos (top 10)
	    List<Top10Summoner> topTen = list.subList(0, Math.min(list.size(), 10));
	    List<Top10SummonerModel> topTenModel = new ArrayList<Top10SummonerModel>();
	    
	    for(Top10Summoner x : topTen) {
	    	topTenModel.add(top10SummonerService.transformToModel(x));
	    }
	    
	    top10SummonerService.updateAll(topTenModel);

	    return ResponseEntity.ok(topTen);

		
	}
	
	@GetMapping(path= "/lol/listopten")
	public ResponseEntity<List<Top10SummonerModel>> listopten(){
		List<Top10SummonerModel> sortedList = top10SummonerService.listAll();
		sortedList.sort(Comparator.comparingInt(Top10SummonerModel::getLeaguePoints).reversed());
		return ResponseEntity.ok(sortedList);
	}
	
	public enum Region {
	    BR1, EUN1, EUW1, JP1, KR, LA1, LA2, NA1, OC1, PH2, RU, SG2, TH2, TR1, VN2
	}
	@GetMapping(path = "/lol/top10byregion")
	public ResponseEntity<List<Top10Summoner>> getTopTen(@RequestParam Region region) {
		RestTemplate restTemplate = new RestTemplate();
		String url = buildChallengerLeagueUrl(region.toString());
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);


        ObjectMapper objectMapper = new ObjectMapper();
        List<Top10Summoner> list = new ArrayList<Top10Summoner>();
        
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode entries = root.get("entries");

            list.addAll(parseTop10Summoners(entries, region.toString()));

        } catch (IOException e) {
            e.printStackTrace();
        }
	    // Tu lógica para obtener el Top 10 con la región proporcionada

	    return ResponseEntity.ok(list);
	}
	
	public String buildChallengerLeagueUrl(String region) {
		String _baseURL = "https://{region}.api.riotgames.com";
	    return UriComponentsBuilder.fromHttpUrl(_baseURL)
	            .path("/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5")
	            .queryParam("api_key", _apiKey)
	            .buildAndExpand(region)
	            .toUriString();
	}
	
	public List<Top10Summoner> parseTop10Summoners(JsonNode entries, String region) {
	    List<Top10Summoner> list = new ArrayList<>();

	    for (JsonNode entry : entries) {
	        String summonerName = entry.get("summonerName").asText();
	        String summonerId = entry.get("summonerId").asText();
	        Integer leaguePoints = entry.get("leaguePoints").asInt();
	        Top10Summoner top10Summoner = new Top10Summoner(summonerId, region, summonerName, leaguePoints);
	        list.add(top10Summoner);
	    }

	    return list;
	}
	
	

}
