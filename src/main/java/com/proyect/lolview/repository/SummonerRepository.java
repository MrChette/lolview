package com.proyect.lolview.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyect.lolview.entity.Summoner;


@Repository("summonerRepository")
public interface SummonerRepository extends JpaRepository<Summoner, Serializable>{

}
