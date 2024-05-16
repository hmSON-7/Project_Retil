package com.project.Retil.userAccount.Repository;

import com.project.Retil.userAccount.Entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User_Information, Long> {

    User_Information findByEmail(String email);

    boolean existsByEmail(String email);
}
