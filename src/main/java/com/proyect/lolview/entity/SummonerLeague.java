package com.proyect.lolview.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummonerLeague {
	
	@Id
	@Column(name="leagueId")
	private String leagueId;
	
	@Column(name="queueType", unique = false, nullable = true)
    private String queueType;
    
	@Column(name="tier", unique = false, nullable = true)
    private String tier;
    
	@Column(name="summone_rank", unique = false, nullable = true)
    private String rank;
    
	@Column(name = "summoner_id", insertable = false, updatable = false)
	private String summonerId;
    
	@Column(name="summonerName", unique = true, nullable = true)
    private String summonerName;
    
	@Column(name="leaguePoints", unique = false, nullable = true)
    private int leaguePoints;
    
	@Column(name="wins", unique = false, nullable = true)
    private int wins;
    
	@Column(name="losses", unique = false, nullable = true)
    private int losses;
    
	@Column(name="veteran", unique = false, nullable = true)
    private boolean veteran;
    
	@Column(name="inactive", unique = false, nullable = true)
    private boolean inactive;
    
	@Column(name="freshBlood", unique = false, nullable = true)
    private boolean freshBlood;
    
	@Column(name="hotStreak", unique = false, nullable = true)
    private boolean hotStreak;
	
	@ManyToOne
	@JoinColumn(name = "summoner_id")
	private Summoner summoner;
	

}
