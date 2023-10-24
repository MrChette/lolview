package com.proyect.lolview.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.proyect.lolview.entity.Summoner;
import com.proyect.lolview.model.SummonerModel;
import com.proyect.lolview.repository.SummonerRepository;
import com.proyect.lolview.service.GenericService;


@Service("summonerServiceImpl")
public class SummonerServiceImpl implements GenericService<Summoner,SummonerModel,Long>{
	
	
	@Autowired
	@Qualifier("summonerRepository")
	private SummonerRepository summonerRespository;

	@Override
	public Summoner addEntity(SummonerModel model) {
		return summonerRespository.save(transform(model));
	}

	@Override
	public boolean removeEntity(Long id) {
		if(summonerRespository.findById(id)!=null) {
			summonerRespository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Summoner updateEntity(SummonerModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Summoner findEntityById(String id) {
	    Optional<Summoner> userOptional = summonerRespository.findById(id);
	    return userOptional.orElse(null);
	}

	@Override
	public SummonerModel findModelById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Summoner transform(SummonerModel model) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(model, Summoner.class);
	}

	@Override
	public SummonerModel transformToModel(Summoner entity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(entity, SummonerModel.class);
	}

	@Override
	public List<SummonerModel> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Summoner findEntityById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
