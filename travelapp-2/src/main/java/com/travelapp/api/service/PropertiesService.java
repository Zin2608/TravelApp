package com.travelapp.api.service;

import java.util.List;

import com.travelapp.api.entity.Properties;
import com.travelapp.api.request.PropertiesRequest;

public interface PropertiesService {
    Properties savePro(PropertiesRequest propertiesRequest);
    List<Properties> getAllPro();
    void deleteAllPro();
    List<Properties> getFavoriteProperties();
    void updateFavoriteStatus(String title, boolean isFavorite);

}