package com.project.Retil.til.repository;

import com.project.Retil.til.entity.Til;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TilRepository extends JpaRepository<Til, Long> {
    ArrayList<Til> findAllById(Long userId);
}
