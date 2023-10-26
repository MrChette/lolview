package com.proyect.lolview.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyect.lolview.entity.Top10Summoner;



@Repository("top10SummonerRepository")
public interface Top10SummonerRepository extends JpaRepository<Top10Summoner, Serializable>{

}
