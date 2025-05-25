package com.codewithak.dream_shop.repository;

import com.codewithak.dream_shop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    public List<Image> findByProductId(Long id);
}
