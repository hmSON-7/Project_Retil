package com.project.Retil.review.repository;

import com.project.Retil.review.entity.Review;
import com.project.Retil.userAccount.Entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByUser(User_Information user);

    Review findByDateAndUser(LocalDate date, User_Information user);

}