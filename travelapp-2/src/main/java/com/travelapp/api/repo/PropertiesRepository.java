package com.travelapp.api.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelapp.api.entity.Properties;

public interface PropertiesRepository extends JpaRepository<Properties, Integer> {
    List<Properties> findByIsFavTrue();
    Optional<Properties> findByTitle(String title);
}