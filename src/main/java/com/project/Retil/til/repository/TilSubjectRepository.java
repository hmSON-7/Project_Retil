package com.project.Retil.til.repository;

import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.userAccount.Entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TilSubjectRepository extends JpaRepository<TilSubject, Long> {
    ArrayList<TilSubject> findAllByUser(User_Information user);
}
