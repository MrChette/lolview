package com.proyect.lolview.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummonerModel {
	
	private String id;
	private String accountId;
	private String puuid;
	private String name;
	private Long profileIconId;
	private Long revisionDate;
	private Long summonerLevel;
	

}
