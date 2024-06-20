package com.project.Retil.til.repository;

import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TilProgressRepository extends JpaRepository<TilProgress, Long> {
    TilProgress findByTil(Til til);
}