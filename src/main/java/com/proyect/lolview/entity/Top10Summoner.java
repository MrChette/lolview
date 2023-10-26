package com.proyect.lolview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Top10Summoner {
	
	@Id
	@Column(name="summonerId")
	private String summonerId;
	
	@Column(name="server", unique = false, nullable = false)
	private String server;
	
	@Column(name="summonerName", unique = false, nullable = false)
    private String summonerName;
    
	@Column(name="leaguePoints", unique = false, nullable = false)
    private Integer leaguePoints;

}


	