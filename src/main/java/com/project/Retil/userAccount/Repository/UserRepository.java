package com.project.Retil.userAccount.Repository;

import com.project.Retil.userAccount.Entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_Information, Long> {

    Optional<User_Information> findByEmail(String email);
}
