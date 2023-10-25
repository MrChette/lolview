package com.proyect.lolview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummonerLeagueModel {
	
	private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private String summonerId;
    private String summonerName;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean veteran;
    private boolean inactive;
    private boolean freshBlood;
    private boolean hotStreak;
    @JsonIgnore
    private String server;
    
    
	public SummonerLeagueModel(String tier, String summonerName, Integer leaguePoints, String server) {
		super();
		this.tier = tier;
		this.summonerName = summonerName;
		this.leaguePoints = leaguePoints;
		this.server = server;
	}


	
    
    

}
