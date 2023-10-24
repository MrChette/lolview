package com.proyect.lolview.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyect.lolview.entity.SummonerLeague;


@Repository("summonerLeagueRepository")
public interface SummonerLeagueRepository extends JpaRepository<SummonerLeague, Serializable>{

}
