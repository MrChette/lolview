package com.proyect.lolview.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.proyect.lolview.entity.Summoner;
import com.proyect.lolview.entity.SummonerLeague;
import com.proyect.lolview.model.SummonerLeagueModel;
import com.proyect.lolview.repository.SummonerLeagueRepository;
import com.proyect.lolview.service.GenericService;


@Service("summonerLeagueServiceImpl")
public class SummonerLeagueServiceImpl implements GenericService<SummonerLeague,SummonerLeagueModel,Long> {
	
	@Autowired
	@Qualifier("summonerLeagueRepository")
	private SummonerLeagueRepository summonerLeagueRepository;

	@Override
	public SummonerLeague addEntity(SummonerLeagueModel model) {
		return summonerLeagueRepository.save(transform(model));
	}

	@Override
	public boolean removeEntity(Long id) {
		if(summonerLeagueRepository.findById(id)!=null) {
			summonerLeagueRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public SummonerLeague updateEntity(SummonerLeagueModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SummonerLeague findEntityById(Long id) {
		Optional<SummonerLeague> userOptional = summonerLeagueRepository.findById(id);
	    return userOptional.orElse(null);
	}

	@Override
	public SummonerLeagueModel findModelById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SummonerLeague transform(SummonerLeagueModel model) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(model, SummonerLeague.class);
	}

	@Override
	public SummonerLeagueModel transformToModel(SummonerLeague entity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(entity, SummonerLeagueModel.class);
	}

	@Override
	public List<SummonerLeagueModel> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Summoner findEntityById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
