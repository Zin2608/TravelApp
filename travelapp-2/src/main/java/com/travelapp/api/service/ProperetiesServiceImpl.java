package com.travelapp.api.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travelapp.api.entity.Properties;
import com.travelapp.api.repo.PropertiesRepository;
import com.travelapp.api.request.PropertiesRequest;

@Service
public class ProperetiesServiceImpl implements PropertiesService {
	
	@Autowired
	PropertiesRepository propertiesRepository;
	
	@Override
	public Properties savePro(PropertiesRequest propertiesRequest) {
		Properties properties = new Properties();
		properties.setTitle(propertiesRequest.getTitle());
		properties.setType(propertiesRequest.getType());
		properties.setAddress(propertiesRequest.getAddress());
		properties.setArea(propertiesRequest.getArea());
		properties.setDescription(propertiesRequest.getDescription());
		properties.setPicPath(propertiesRequest.getPicPath());
		properties.setPrice(propertiesRequest.getPrice());
		properties.setBed(propertiesRequest.getBed());
		properties.setBath(propertiesRequest.getBath());
		properties.setGarage(propertiesRequest.isGarage());
		properties.setScore(propertiesRequest.getScore());
		properties.setFav(propertiesRequest.isFav());
		return propertiesRepository.save(properties);
	}
	
	@Override
	public List<Properties> getAllPro() {
	    return propertiesRepository.findAll();
	}

	@Override
	public void deleteAllPro() {
	    propertiesRepository.deleteAll(); 
	}
	
	 @Override
	 public List<Properties> getFavoriteProperties() {
	        return propertiesRepository.findByIsFavTrue();
	 }
	 
	 @Override
	    public void updateFavoriteStatus(String title, boolean isFavorite) {
	        Optional<Properties> optionalProperty = propertiesRepository.findByTitle(title);
	        if (optionalProperty.isPresent()) {
	            Properties property = optionalProperty.get();
	            property.setFav(isFavorite);
	            propertiesRepository.save(property);
	        } else {
	            throw new NoSuchElementException("Property not found with title: " + title);
	        }
	    }
	
}
