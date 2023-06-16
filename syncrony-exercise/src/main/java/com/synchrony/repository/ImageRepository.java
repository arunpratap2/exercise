package com.synchrony.repository;

import com.synchrony.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String> {
    List<Image> findByUsername(String userName);

}
