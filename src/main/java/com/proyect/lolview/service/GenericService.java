package com.proyect.lolview.service;

import java.util.List;

import com.proyect.lolview.entity.Summoner;

public interface GenericService<T, M, ID>  {
	
	T addEntity(M model);
    boolean removeEntity(ID id);
    T updateEntity(M model);
    T findEntityById(ID id);
    M findModelById(ID id);
    T transform(M model);
    M transformToModel(T entity);
    List<M> listAll();
	Summoner findEntityById(String id);

}
