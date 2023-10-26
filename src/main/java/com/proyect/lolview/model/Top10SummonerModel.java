package com.proyect.lolview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Top10SummonerModel {
	
	private String summonerId;
	private String server;
    private String summonerName;
    private Integer leaguePoints;

}


	