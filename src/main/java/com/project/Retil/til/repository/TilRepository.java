package com.project.Retil.til.repository;

import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.userAccount.Entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
@Repository
public interface TilRepository extends JpaRepository<Til, Long> {
    ArrayList<Til> findAllByUser(User_Information user);

    ArrayList<Til> findAllByTilSubject(TilSubject tilSubject);

    Optional<Til> findByIdAndUserId(Long tilId, Long userId);
}
