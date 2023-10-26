package com.proyect.lolview.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.proyect.lolview.entity.Summoner;
import com.proyect.lolview.entity.Top10Summoner;
import com.proyect.lolview.model.Top10SummonerModel;
import com.proyect.lolview.repository.Top10SummonerRepository;
import com.proyect.lolview.service.GenericService;


@Service("top10SummonerServiceImpl")
public class Top10SummonerServiceImpl implements GenericService<Top10Summoner,Top10SummonerModel,Long>{
	
	
	@Autowired
	@Qualifier("top10SummonerRepository")
	private Top10SummonerRepository top10SummonerRepository;

	
	public List<Top10Summoner> updateAll(List<Top10SummonerModel> models) {
        // Elimina todas las entradas existentes
        top10SummonerRepository.deleteAll();

        List<Top10Summoner> entities = new ArrayList<>();
        for (Top10SummonerModel model : models) {
            Top10Summoner entity = transform(model);
            entities.add(entity);
        }

        // Guarda las nuevas entradas
        return top10SummonerRepository.saveAll(entities);
    }

	@Override
	public Top10Summoner addEntity(Top10SummonerModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeEntity(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Top10Summoner updateEntity(Top10SummonerModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Top10Summoner findEntityById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Top10SummonerModel findModelById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Top10Summoner transform(Top10SummonerModel model) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(model, Top10Summoner.class);
	}

	@Override
	public Top10SummonerModel transformToModel(Top10Summoner entity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(entity, Top10SummonerModel.class);
	}

	@Override
	public List<Top10SummonerModel> listAll() {
		List<Top10SummonerModel> modelList = new ArrayList<Top10SummonerModel>();
		List<Top10Summoner> entityList =  top10SummonerRepository.findAll();
		for(Top10Summoner entity : entityList) {
			modelList.add(transformToModel(entity));
		}
		return modelList;
	}

	@Override
	public Summoner findEntityById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
