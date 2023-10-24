package com.proyect.lolview.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Summoner {
	
	@Id
	@Column(name="id")
	private String id;
	
	
	@Column(name="accountId", unique = true, nullable = true)
	private String accountId;
	
	@Column(name="puuid", unique = true, nullable = true)
	private String puuid;
	
	@Column(name="name", unique = true, nullable = true)
	private String name;
	
	@Column(name="profileIconId", unique = true, nullable = true)
	private Long profileIconId;
	
	@Column(name="revisionDate", unique = true, nullable = true)
	private Long revisionDate;
	
	@Column(name="summonerLevel", unique = true, nullable = true)
	private Long summonerLevel;
	
	
	@OneToMany(mappedBy = "summoner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SummonerLeague> summonerLeagues;
	

}

