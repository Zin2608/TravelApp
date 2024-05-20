package com.travelapp.api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelapp.api.entity.Properties;
import com.travelapp.api.request.PropertiesRequest;
import com.travelapp.api.service.PropertiesService;

@RestController
@RequestMapping("/api")
public class PropertiesController {

	@Autowired
	PropertiesService propertiesService;
	
	@PostMapping("/addPro")
	public ResponseEntity<String> savePro(@RequestBody PropertiesRequest propertiesRequest){
		Properties properties = propertiesService.savePro(propertiesRequest);
		if (properties != null) {
			return new ResponseEntity<>("User added...", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/allPro")
    public ResponseEntity<List<Properties>> getAllPro() {
        List<Properties> propertiesList = propertiesService.getAllPro();
        if (!propertiesList.isEmpty()) {
            return new ResponseEntity<>(propertiesList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
	
	@GetMapping("/favoritePro")
	public ResponseEntity<List<Properties>> getFavoritePro() {
	    List<Properties> favoritePropertiesList = propertiesService.getFavoriteProperties();
	    if (!favoritePropertiesList.isEmpty()) {
	        return new ResponseEntity<>(favoritePropertiesList, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	}
	
	@PostMapping("/updateFav")
    public ResponseEntity<String> updateFavoriteStatus(@RequestParam String title, @RequestParam boolean isFavorite) {
        try {
            propertiesService.updateFavoriteStatus(title, isFavorite);
            return new ResponseEntity<>("Favorite status updated successfully", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update favorite status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@GetMapping("/deleteAllPro")
	public ResponseEntity<String> deleteAllPro() {
	    propertiesService.deleteAllPro();
	    return new ResponseEntity<>("All properties deleted successfully", HttpStatus.OK);
	}


}
