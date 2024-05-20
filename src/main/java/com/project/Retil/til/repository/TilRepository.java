package com.project.Retil.til.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TilRepository extends JpaRepository<Til, Long> {
    @Override
    ArrayList<Til> findAllById(Long userId);
}
